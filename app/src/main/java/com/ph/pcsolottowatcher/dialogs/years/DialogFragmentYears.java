package com.ph.pcsolottowatcher.dialogs.years;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;

public class DialogFragmentYears extends DialogFragment {
  private final String[] years = {
    "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013"
  };
  private AllResultsViewModel all_results_viewModel;
  private int selected = 0;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.all_results_viewModel =
        new ViewModelProvider(getActivity()).get(AllResultsViewModel.class);
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Sort by year");
    builder.setSingleChoiceItems(
        years,
        selected,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            selected = which;
          }
        });
    builder.setPositiveButton(
        "Select",
        (DialogInterface dialog, int id) -> {
          all_results_viewModel.setYear(years[selected]);
        });
    builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {});

    return builder.create();
  }
}
