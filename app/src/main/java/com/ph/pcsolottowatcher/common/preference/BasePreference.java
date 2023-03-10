package com.ph.pcsolottowatcher.common.preference;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

public abstract class BasePreference<Presenter extends BasePreferencePresenter>
    extends PreferenceFragmentCompat {

  protected Presenter presenter;

  @NonNull protected abstract Presenter createPresenter();

  protected abstract void injectDependencies();

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();

    presenter = createPresenter();
  }
}
