package com.ph.pcsolottowatcher.activities.main;

import com.ph.pcsolottowatcher.common.activity.BaseActivityView;
import java.util.List;

public interface MainActivityView extends BaseActivityView {

  void getSearchHistory(List<String> list);

  void getProfile(String profile);

  void onEmailVerification(boolean verified, String email);
}
