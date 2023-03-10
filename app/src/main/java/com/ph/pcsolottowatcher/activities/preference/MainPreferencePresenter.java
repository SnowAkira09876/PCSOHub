package com.ph.pcsolottowatcher.activities.preference;

import com.ph.pcsolottowatcher.common.activity.BaseActivityPresenter;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;

public class MainPreferencePresenter extends BaseActivityPresenter<MainPreferenceView> {
  private BaseActivityPresenter.Repo base;

  public MainPreferencePresenter(MainPreferenceView view, PrefHelper prefHelper) {
    super(view);
    this.base = new BaseActivityPresenter.Repo<MainPreferenceView>(view, prefHelper);
  }

  @Override
  public void applyDynamicTheme() {
    base.applyDynamicTheme();
  }
}
