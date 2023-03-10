package com.ph.pcsolottowatcher.bottomnav.tab2.post;

import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheetPresenter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PostPresenter extends BaseBottomSheetPresenter<PostView> {
  private ResultsRepository repo;

  public PostPresenter(PostView view, JsonHelper jsonHelper, FeedDataHelper feedDataHelper) {
    super(view);
    this.repo = new ResultsRepository(view, jsonHelper, feedDataHelper);
  }

  public void getRegex() {
    repo.getRegex();
  }

  public void postNumber(PostItemModel model) {
    repo.postNumber(model);
  }

  private static class ResultsRepository {
    private WeakReference<PostView> view;
    private JsonHelper jsonHelper;
    private FeedDataHelper feedDataHelper;

    public ResultsRepository(PostView view, JsonHelper jsonHelper, FeedDataHelper feedDataHelper) {
      this.view = new WeakReference<>(view);
      this.jsonHelper = jsonHelper;
      this.feedDataHelper = feedDataHelper;
    }

    public void getRegex() {
      Map<String, String> regex = new HashMap<>();
      Consumer<List<LottoGameBaseModel>> consumer =
          list -> {
            list.stream().forEach(model -> regex.put(model.getRegex(), model.getName()));
            view.get().getRegex(regex);
          };

      jsonHelper.getLottoGamesList(consumer, JsonHelper.LOTTO_ARRAY_NAME);
    }

    public void postNumber(PostItemModel model) {
      BiConsumer<Integer, String> consumer =
          (result, message) -> {
            switch (result) {
              case -1:
                view.get().postResult(-1, message);
                break;
              case 0:
                view.get().postResult(0, message);
                break;
              case 1:
                view.get().postResult(1, message);
                break;
            }
          };

      feedDataHelper.setNumber(model, consumer);
    }
  }
}
