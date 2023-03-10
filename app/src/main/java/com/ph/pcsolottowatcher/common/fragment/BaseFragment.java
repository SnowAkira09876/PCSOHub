package com.ph.pcsolottowatcher.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class BaseFragment<Presenter extends BaseFragmentPresenter> extends Fragment {

  protected Presenter presenter;
  @IdRes protected int container;
  @NonNull protected View view;

  protected FragmentManager fragmentManager;

  @NonNull protected abstract Presenter createPresenter();

  protected abstract void onsetViewBinding();

  protected abstract void onsetViews();

  protected abstract void injectDependencies();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();

    presenter = createPresenter();
    fragmentManager = getParentFragmentManager();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    onsetViewBinding();
    onsetViews();
    return view;
  }

  protected void displayHistoryFragment(BaseFragment fragment, Bundle historyBundle) {
    fragment.setArguments(historyBundle);

    fragmentManager
        .beginTransaction()
        .setReorderingAllowed(true)
        .replace(container, fragment, null)
        .commit();
  }

  protected void removeFragment(BaseFragment fragment) {
    if (fragment.isVisible()) fragmentManager.beginTransaction().remove(fragment).commit();
  }
}
