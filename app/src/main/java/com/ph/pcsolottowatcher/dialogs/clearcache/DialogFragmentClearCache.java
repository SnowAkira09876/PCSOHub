package com.ph.pcsolottowatcher.dialogs.clearcache;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.dialog.BaseDialogFragment;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;

public class DialogFragmentClearCache extends BaseDialogFragment<DialogFragmentClearCachePresenter>
    implements DialogFragmentClearCacheView {
  private MainPreferenceViewModel viewModel;

  PostCache postCache;

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainPreferenceViewModel.class);
  }

  @Override
  protected void onsetViewBinding() {}

  @Override
  protected Dialog onsetDialog() {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle(getString(R.string.clear_cache));
    builder.setMessage(getString(R.string.are_you_sure_you_want_to_clear_cache));
    builder.setPositiveButton(
        getString(R.string.clear_cache),
        (DialogInterface dialog, int id) -> {
          presenter.clearCache();
        });

    builder.setNegativeButton(getString(R.string.cancel), (DialogInterface dialog, int id) -> {});

    AlertDialog dialog = builder.create();

    return dialog;
  }

  @Override
  protected void onsetViews() {}

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();
    this.postCache = appComponent.getPostCache();
  }

  @Override
  protected DialogFragmentClearCachePresenter createPresenter() {
    return new DialogFragmentClearCachePresenter(this, postCache);
  }

  @Override
  public void onClearCache() {
    viewModel.setMessage("Cache cleared successfully");
  }
}
