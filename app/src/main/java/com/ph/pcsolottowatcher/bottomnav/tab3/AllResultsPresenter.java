package com.ph.pcsolottowatcher.bottomnav.tab3;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentPresenter;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AllResultsPresenter extends BaseFragmentPresenter<AllResultsView> {
  private ResultsRepository repo;

  public AllResultsPresenter(AllResultsView view, JsonHelper jsonHelper) {
    super(view);
    this.repo = new ResultsRepository(view, jsonHelper);
  }

  public void getGames() {
    repo.getGames();
  }

  public void getAllResults(String filter) {
    repo.setFilterName(filter).getAllResults();
  }

  private static class ResultsRepository {
    private WeakReference<AllResultsView> view;
    private JsonHelper jsonHelper;
    private String filterName;

    public ResultsRepository(AllResultsView view, JsonHelper jsonHelper) {
      this.view = new WeakReference<>(view);
      this.jsonHelper = jsonHelper;
    }

    public void getGames() {
      Consumer<List<LottoGameBaseModel>> consumer =
          list -> {
            view.get().getGames(list);
          };
      jsonHelper.getPCSOGOVGamesList(consumer, JsonHelper.PCSOGOV_ARRAY_NAME);
    }

    public void getAllResults() {
      Consumer<List<LocalHistoryModel>> consumer =
          list -> {
            CompletableFuture<ArrayList<LocalHistoryModel>> future =
                CompletableFuture.supplyAsync(
                    () -> {
                      ArrayList<LocalHistoryModel> filtered_list =
                          list.stream()
                              .filter(model -> model.getName().equals(filterName))
                              .collect(Collectors.toCollection(ArrayList::new));
                      return filtered_list;
                    });

            future.thenAccept(
                filtered_list -> {
                  view.get().getFilteredList(filtered_list);
                });
          };

      jsonHelper.getAllResults(consumer);
    }

    public ResultsRepository setFilterName(String filterName) {
      this.filterName = filterName;
      return this;
    }
  }
}
