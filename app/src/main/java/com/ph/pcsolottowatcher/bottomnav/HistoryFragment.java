package com.ph.pcsolottowatcher.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.FragmentHistoryBinding;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import com.ph.pcsolottowatcher.viewmodels.ResultsViewModel;
import java.util.ArrayList;
import java.util.List;

public abstract class HistoryFragment<T extends BaseHistoryModel>
    extends BaseFragment<HistoryPresenter<T>> implements HistoryView<T> {
  protected RecyclerView rv;
  protected BaseListAdapter<T> rootListAdapter;
  private LinearLayoutManager llm;
  protected List<T> list;
  private FragmentHistoryBinding binding;

  private MainActivityViewModel viewModel;
  private ResultsViewModel results_viewModel;
  private AllResultsViewModel all_results_viewModel;

  protected abstract BaseListAdapter<T> getAdapter();

  protected abstract void onsetBundle();

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.list = new ArrayList<>();

    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    this.results_viewModel = new ViewModelProvider(getActivity()).get(ResultsViewModel.class);
    this.all_results_viewModel =
        new ViewModelProvider(getActivity()).get(AllResultsViewModel.class);

    onsetBundle();
  }

  @Override
  protected void injectDependencies() {}

  @NonNull @Override
  protected HistoryPresenter<T> createPresenter() {
    return new HistoryPresenter<>(this);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.binding = FragmentHistoryBinding.inflate(inflater, container, false);
    onsetViewBinding();
    onsetViews();
    return binding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.getHistoryList();
  }

  @Override
  public void occupyRecyclerView(List<T> list) {
    rootListAdapter.submitList(list);
  }

  @Override
  public void filteredHistory(List<T> list) {
    rootListAdapter.submitList(list);
  }

  public void search(String sText) {
    presenter.getFilteredList(sText);
  }

  protected void onsetViewBinding() {
    this.rv = binding.rv;
  }

  protected void onsetViews() {
    this.llm = new LinearLayoutManager(getActivity());
    this.rootListAdapter = getAdapter();

    rv.setLayoutManager(llm);
    rv.setAdapter(rootListAdapter);

    results_viewModel
        .getSearch()
        .observe(
            getViewLifecycleOwner(),
            search -> {
              presenter.getFilteredList(search);
            });

    all_results_viewModel
        .getSearch()
        .observe(
            getViewLifecycleOwner(),
            search -> {
              presenter.getFilteredList(search);
            });

    all_results_viewModel
        .getYear()
        .observe(
            getViewLifecycleOwner(),
            year -> {
              presenter.getFilteredList(year);
            });

    all_results_viewModel
        .getDate()
        .observe(
            getViewLifecycleOwner(),
            date -> {
              presenter.getFilteredList(date);
            });

    rv.addOnScrollListener(
        new RecyclerView.OnScrollListener() {
          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) {
              // SLIDEDOWN
              viewModel.setFab(false);
            }
            if (dy < 0) {
              // SLIDEUP
              viewModel.setFab(true);
            }
          }
        });
  }
}
