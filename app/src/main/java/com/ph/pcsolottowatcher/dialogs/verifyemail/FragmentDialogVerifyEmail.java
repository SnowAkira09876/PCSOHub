package com.ph.pcsolottowatcher.dialogs.verifyemail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class FragmentDialogVerifyEmail extends DialogFragment {
  private String head = "We have sent an email to ";
  private String message =
      ". To continue using PCSO Hub, please verify your account by checking your inbox for a message from us. Open the message and follow the instructions to confirm your email address.";
  private String email = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.email = getArguments() != null ? getArguments().getString("email") : "unknown";
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Verification");
    builder.setMessage(head + email + message);
    builder.setPositiveButton("Got it", (DialogInterface dialog, int id) -> {});
    return builder.create();
  }
}
