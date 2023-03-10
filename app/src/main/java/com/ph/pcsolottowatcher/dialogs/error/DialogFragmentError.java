package com.ph.pcsolottowatcher.dialogs.error;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogFragmentError extends DialogFragment {
  private String message;
  private final String link = "https://www.facebook.com/profile.php?id=100087796637987";

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.message = getArguments().getString("message");
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
    builder.setTitle("Error");
    builder.setMessage(message);
    builder.setPositiveButton("Got it", (DialogInterface dialog, int id) -> {});
    builder.setNegativeButton(
        "Report",
        (DialogInterface dialog, int id) -> {
          startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link)));
        });

    return builder.create();
  }
}
