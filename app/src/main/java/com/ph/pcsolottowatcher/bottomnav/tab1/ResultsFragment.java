package com.ph.pcsolottowatcher.bottomnav.tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.data.jsoup.results.DParser;
import com.ph.pcsolottowatcher.data.jsoup.results.OneTimeParser;
import com.ph.pcsolottowatcher.data.jsoup.results.SixDigitsParser;
import com.ph.pcsolottowatcher.data.jsoup.results.ThreeTimeParser;
import com.ph.pcsolottowatcher.data.sql.cache.DHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.OneTimeHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.SixDigitsHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.ThreeTimeHistoryCache;
import com.ph.pcsolottowatcher.databinding.FragmentResultsBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.dialogs.error.DialogFragmentError;
import com.ph.pcsolottowatcher.fragments.tab1.d.FragmentDHistory;
import com.ph.pcsolottowatcher.fragments.tab1.onetime.FragmentOneTimeHistory;
import com.ph.pcsolottowatcher.fragments.tab1.sixdigits.FragmentSixDigitsHistory;
import com.ph.pcsolottowatcher.fragments.tab1.threetime.FragmentThreeTimeHistory;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import com.ph.pcsolottowatcher.viewmodels.ResultsViewModel;
import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends BaseFragment<ResultsPresenter> implements ResultsView {
  private FragmentResultsBinding binding;
  private BaseFragment currentFragment,
      sixDigitsHistory,
      dHistory,
      threeTimeHistory,
      oneTimeHistory;
  private Bundle historyBundle;
  private SwipeRefreshLayout swipeRefresh;
  private LottoGameBaseModel model;
  private MainActivityViewModel viewModel;
  private ResultsViewModel results_viewModel;
  private List<LottoGameBaseModel> list;
  private final DialogFragmentError dialogError = new DialogFragmentError();

  SixDigitsParser sixDigitsParser;
  DParser dParser;
  OneTimeParser oneTimeParser;
  ThreeTimeParser threeTimeParser;

  DHistoryCache dHistoryCache;
  SixDigitsHistoryCache sixDigitsHistoryCache;
  OneTimeHistoryCache oneTimeHistoryCache;
  ThreeTimeHistoryCache threeTimeHistoryCache;
  ExecutorHelper executor;

  JsonHelper jsonHelper;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.list = new ArrayList<>();
    this.model = new LottoGameBaseModel();
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    this.results_viewModel = new ViewModelProvider(getActivity()).get(ResultsViewModel.class);
    this.container = R.id.results_container;
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.binding = FragmentResultsBinding.inflate(inflater, container, false);
    this.view = binding.getRoot();

    results_viewModel.setPlaceHolder(true);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onPause() {
    super.onPause();

    if (historyBundle != null) this.historyBundle.clear();
  }

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();

    this.jsonHelper = appComponent.getJsonHelper();
    this.dHistoryCache = appComponent.getDHistoryCache();
    this.sixDigitsHistoryCache = appComponent.getSixDigitsHistoryCache();
    this.oneTimeHistoryCache = appComponent.getOneTimeHistoryCache();
    this.threeTimeHistoryCache = appComponent.getThreeTimeHistoryCache();
    this.executor = appComponent.getExecutorHelper();

    this.sixDigitsParser = appComponent.getSixDigitsParser();
    this.dParser = appComponent.getDParser();
    this.oneTimeParser = appComponent.getOneTimeParser();
    this.threeTimeParser = appComponent.getThreeTimeParser();
  }

  @NonNull @Override
  protected ResultsPresenter createPresenter() {
    return new ResultsPresenter(
        this,
        jsonHelper,
        dHistoryCache,
        sixDigitsHistoryCache,
        oneTimeHistoryCache,
        threeTimeHistoryCache,
        executor,
        sixDigitsParser,
        dParser,
        oneTimeParser,
        threeTimeParser);
  }

  @Override
  protected void onsetViewBinding() {
    this.swipeRefresh = binding.swipeRefresh;
  }

  @Override
  protected void onsetViews() {
    swipeRefresh.setOnRefreshListener(
        () -> {
          presenter.getLottoHistory(model, true);
        });

    results_viewModel
        .getModel()
        .observe(
            getViewLifecycleOwner(),
            model -> {
              this.model = model;
              results_viewModel.setPlaceHolder(false);
              onBusyParsing(true);

              presenter.getLottoHistory(model, false);
            });

    presenter.getGames();
  }

  @Override
  public void getGames(List<LottoGameBaseModel> list) {
    viewModel.setGames(list);
  }

  @Override
  public void showError(String message) {
    if (currentFragment != null) removeFragment(currentFragment);

    results_viewModel.setPlaceHolder(true);

    if (!dialogError.isAdded()) {
      Bundle bundle = new Bundle();
      bundle.putString("message", message);
      dialogError.setArguments(bundle);
      dialogError.show(getParentFragmentManager(), null);
    }
  }

  @Override
  public void getSixDigitHistory(ArrayList<SixDigitsHistoryModel> sixDigitList) {
    this.sixDigitsHistory = new FragmentSixDigitsHistory();
    this.historyBundle = new Bundle();
    historyBundle.putParcelableArrayList("SixDigitsHistoryList", sixDigitList);
    displayHistoryFragment(sixDigitsHistory, historyBundle);
  }

  @Override
  public void getDHistory(ArrayList<DHistoryModel> dList) {
    this.dHistory = new FragmentDHistory();
    this.historyBundle = new Bundle();
    historyBundle.putParcelableArrayList("DHistoryList", dList);
    displayHistoryFragment(dHistory, historyBundle);
  }

  @Override
  public void getThreeTimeHistory(ArrayList<ThreeTimeHistoryModel> threeTimeHistoryList) {
    this.threeTimeHistory = new FragmentThreeTimeHistory();
    this.historyBundle = new Bundle();
    historyBundle.putParcelableArrayList("ThreeTimeHistoryList", threeTimeHistoryList);
    displayHistoryFragment(threeTimeHistory, historyBundle);
  }

  @Override
  public void getOneTimeHistory(ArrayList<OneTimeHistoryModel> oneTimeHistoryList) {
    this.oneTimeHistory = new FragmentOneTimeHistory();
    this.historyBundle = new Bundle();
    historyBundle.putParcelableArrayList("OneTimeHistoryList", oneTimeHistoryList);
    displayHistoryFragment(oneTimeHistory, historyBundle);
  }

  @Override
  public void onLastUpdatedTime(String time) {
    viewModel.setMessage(time);
  }

  @Override
  protected void displayHistoryFragment(BaseFragment fragment, Bundle historyBundle) {
    super.displayHistoryFragment(fragment, historyBundle);
    this.currentFragment = fragment;
  }

  @Override
  public void showProgress(boolean show) {
    if (show) swipeRefresh.setRefreshing(true);
    else swipeRefresh.setRefreshing(false);
  }

  @Override
  public void onBusyParsing(boolean busy) {
    results_viewModel.setJsoupBusy(busy);
  }
}
