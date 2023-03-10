package com.ph.pcsolottowatcher.common.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheet<Presenter extends BaseBottomSheetPresenter>
    extends BottomSheetDialogFragment {
  protected Presenter presenter;
  @NonNull protected View view;

  @NonNull protected abstract Presenter createPresenter();

  protected abstract void onsetViewBinding();

  protected abstract void onsetViews();

  protected abstract void injectDependencies();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();

    presenter = createPresenter();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
    onsetViewBinding();
    onsetViews();
    return view;
  }
}
