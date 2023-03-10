package com.ph.pcsolottowatcher.common.activity;

import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import java.lang.ref.WeakReference;

public abstract class BaseActivityPresenter<V extends BaseActivityView> {
  protected V view;

  public BaseActivityPresenter(V view) {
    this.view = view;
  }

  protected abstract void applyDynamicTheme();

  public static class Repo<V extends BaseActivityView> {
    private WeakReference<V> view;
    private PrefHelper prefHelper;

    public Repo(V view, PrefHelper prefHelper) {
      this.view = new WeakReference<>(view);
      this.prefHelper = prefHelper;
    }

    public void applyDynamicTheme() {
      boolean dynamic = prefHelper.getBoolean("pref_dynamic_key", false);

      if (dynamic) view.get().applyDynamicTheme(true);
      else view.get().applyDynamicTheme(false);
    }
  }
}
