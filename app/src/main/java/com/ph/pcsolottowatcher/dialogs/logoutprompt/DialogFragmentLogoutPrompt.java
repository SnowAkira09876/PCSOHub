package com.ph.pcsolottowatcher.dialogs.logoutprompt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;

public class DialogFragmentLogoutPrompt extends DialogFragment {
  private MainPreferenceViewModel viewModel;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainPreferenceViewModel.class);
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Logout");
    builder.setMessage("Are you sure you want to logout?");
    builder.setPositiveButton(
        "Logout",
        (DialogInterface dialog, int id) -> {
          AuthUI.getInstance()
              .signOut(getActivity())
              .addOnCompleteListener(
                  task -> {
                    if (task.isSuccessful()) {
                      viewModel.setMessage("Logout successfully");
                    }
                  });
        });
    builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {});

    return builder.create();
  }
}
