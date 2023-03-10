package com.ph.pcsolottowatcher.dialogs.exit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogFragmentExit extends DialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Exit app");
    builder.setMessage("Are you sure you want to exit?");
    builder.setPositiveButton(
        "Exit",
        (DialogInterface dialog, int id) -> {
          getActivity().finishAndRemoveTask();
        });
    builder.setNegativeButton(
        "Minimize",
        (DialogInterface dialog, int id) -> {
          getActivity().moveTaskToBack(true);
        });

    return builder.create();
  }
}
