package com.ph.pcsolottowatcher.preference;

import com.ph.pcsolottowatcher.common.preference.BasePreferenceView;

public interface MainSettingsFragmentView extends BasePreferenceView {
  void getInfo(String name, String email);

  void onUploading(int progress, boolean uploading);

  void setMessage(String message);

  void onEmailVerification(boolean verify, String email);
}
