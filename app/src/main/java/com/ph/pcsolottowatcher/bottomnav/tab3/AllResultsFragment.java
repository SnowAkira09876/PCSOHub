package com.ph.pcsolottowatcher.bottomnav.tab3;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.bottomnav.tab3.allresults.FragmentAllResultsHistory;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.databinding.FragmentAllResultsBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AllResultsFragment extends BaseFragment<AllResultsPresenter>
    implements AllResultsView {

  private FragmentAllResultsBinding binding;
  private BaseFragment allResultsHistory;
  private Bundle historyBundle;
  private AllResultsViewModel all_results_viewModel;
  private MainActivityViewModel viewModel;
  private List<LottoGameBaseModel> games;

  JsonHelper jsonHelper;

  @Inject Handler handler;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();

    this.jsonHelper = appComponent.getJsonHelper();
    appComponent.inject(this);
  }

  @Override
  protected AllResultsPresenter createPresenter() {
    return new AllResultsPresenter(this, jsonHelper);
  }

  @Override
  protected void onsetViewBinding() {}

  @Override
  protected void onsetViews() {
    all_results_viewModel
        .getModel()
        .observe(
            getViewLifecycleOwner(),
            model -> {
              all_results_viewModel.setPlaceHolder(false);
              all_results_viewModel.setJsonBusy(true);

              presenter.getAllResults(model.getName());
            });

    presenter.getGames();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.container = R.id.all_history_container;
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    this.all_results_viewModel =
        new ViewModelProvider(getActivity()).get(AllResultsViewModel.class);
    this.games = new ArrayList<>();
    all_results_viewModel.setPlaceHolder(true);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.binding = FragmentAllResultsBinding.inflate(inflater, container, false);
    this.view = binding.getRoot();
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onPause() {
    super.onPause();

    if (historyBundle != null) this.historyBundle.clear();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    all_results_viewModel.reset();
  }

  @Override
  public void getGames(List<LottoGameBaseModel> games) {
    viewModel.setGames(games);
  }

  @Override
  public void getFilteredList(ArrayList<LocalHistoryModel> list) {
    handler.post(
        () -> {
          all_results_viewModel.setJsonBusy(false);
        });

    this.historyBundle = new Bundle();
    this.allResultsHistory = new FragmentAllResultsHistory();
    historyBundle.putParcelableArrayList("AllResultsHistoryList", list);
    displayHistoryFragment(allResultsHistory, historyBundle);
  }
}
