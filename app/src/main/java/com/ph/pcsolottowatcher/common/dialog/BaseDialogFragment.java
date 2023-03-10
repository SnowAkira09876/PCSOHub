package com.ph.pcsolottowatcher.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.ph.pcsolottowatcher.common.activity.BaseActivityView;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;

public abstract class BaseDialogFragment<Presenter extends BaseDialogPresenter>
    extends DialogFragment {

  protected Presenter presenter;
  protected BaseActivityView baseActivityView;
  protected BaseFragment parentFragment;

  protected abstract void onsetViewBinding();

  protected abstract Dialog onsetDialog();

  protected abstract void onsetViews();

  protected abstract void injectDependencies();

  public void setParentFragmentListener(@NonNull BaseFragment parentFragment) {
    this.parentFragment = parentFragment;
  }

  @NonNull protected abstract Presenter createPresenter();

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    injectDependencies();

    this.presenter = createPresenter();
    this.baseActivityView = (BaseActivityView) getActivity();
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    onsetViewBinding();
    onsetViews();
    return onsetDialog();
  }
}
