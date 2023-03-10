package com.ph.pcsolottowatcher.dialogs.sort;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.dialogs.datepicker.DialogFragmentDate;
import com.ph.pcsolottowatcher.dialogs.years.DialogFragmentYears;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;

public class DialogFragmentSort extends DialogFragment {
  private final String[] sort = {"Sort by year", "Sort by date"};
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
    builder.setTitle("Sort method");
    builder.setSingleChoiceItems(
        sort,
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
          switch (selected) {
            case 0:
              DialogFragmentYears dialogYears = new DialogFragmentYears();
              dialogYears.show(getParentFragmentManager(), "DialogFragmentYears");
              break;
            case 1:
              DialogFragmentDate dialogDate = new DialogFragmentDate();
              dialogDate.show(getParentFragmentManager(), "DialogFragmentDate");
              break;
          }
        });

    builder.setNegativeButton(
        "Reset",
        (DialogInterface dialog, int id) -> {
          all_results_viewModel.reset();
        });

    return builder.create();
  }
}
