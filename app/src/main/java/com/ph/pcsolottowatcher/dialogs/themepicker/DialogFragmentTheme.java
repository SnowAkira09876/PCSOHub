package com.ph.pcsolottowatcher.dialogs.themepicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.dialog.BaseDialogFragment;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;

public class DialogFragmentTheme extends BaseDialogFragment<DialogFragmentThemePresenter>
    implements DialogFragmentThemeView {
  private final String[] themes = {"Light Theme", "Dark Theme", "Follow System"};
  private int selected;
  private MainPreferenceViewModel viewModel;

  PrefHelper prefHelper;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainPreferenceViewModel.class);
    presenter.onCurrentTheme();
  }

  @Override
  protected void onsetViewBinding() {}

  @Override
  protected Dialog onsetDialog() {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Change Theme");

    builder.setSingleChoiceItems(
        themes,
        selected,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            selected = which;
          }
        });
    builder.setPositiveButton(
        "Okay",
        (DialogInterface dialog, int id) -> {
          switch (selected) {
            case 0:
              presenter.setTheme("pref_theme_key", "val_light");
              break;
            case 1:
              presenter.setTheme("pref_theme_key", "val_dark");
              break;
            case 2:
              presenter.setTheme("pref_theme_key", "val_system");
              break;
          }
          dismiss();
        });
    builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {});

    return builder.create();
  }

  @Override
  protected void onsetViews() {}

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();
    this.prefHelper = appComponent.getPrefHelper();
  }

  @Override
  protected DialogFragmentThemePresenter createPresenter() {
    return new DialogFragmentThemePresenter(this, prefHelper);
  }

  @Override
  public void onThemeChanged(String theme) {
    new StartApplication().setAppTheme(theme);
    viewModel.setMessage("You switched to " + themes[selected]);
  }

  @Override
  public void onCurrentTheme(int selected) {
    this.selected = selected;
  }
}
