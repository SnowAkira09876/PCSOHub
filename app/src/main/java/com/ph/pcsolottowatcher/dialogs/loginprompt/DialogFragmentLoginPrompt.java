package com.ph.pcsolottowatcher.dialogs.loginprompt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.pcsolottowatcher.activities.preference.MainPreferenceActivity;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;

public class DialogFragmentLoginPrompt extends DialogFragment {
  MainActivityViewModel viewModel;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Join us!");
    builder.setMessage(
        "You are currently not logged in. Share your lucky numbers and follow other players to stay updated on their luck. Don't miss out on the fun!");
    builder.setPositiveButton(
        "Okay",
        (DialogInterface dialog, int id) -> {
          startActivity(new Intent(getActivity(), MainPreferenceActivity.class));
        });

    return builder.create();
  }
}
