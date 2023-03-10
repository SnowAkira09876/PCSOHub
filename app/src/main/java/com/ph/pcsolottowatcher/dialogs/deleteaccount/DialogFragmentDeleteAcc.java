package com.ph.pcsolottowatcher.dialogs.deleteaccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;

public class DialogFragmentDeleteAcc extends DialogFragment {
  private MainPreferenceViewModel viewModel;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainPreferenceViewModel.class);
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Delete");
    builder.setMessage("Are you sure you want to delete your account? This cannot be undone.");
    builder.setPositiveButton(
        "Delete",
        (DialogInterface dialog, int id) -> {
          AuthUI.getInstance()
              .delete(getActivity())
              .addOnCompleteListener(
                  task -> {
                    if (task.isSuccessful()) {
                      viewModel.setMessage("Account successfully deleted");
                    }
                  });
        });
    builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {});

    return builder.create();
  }
}
