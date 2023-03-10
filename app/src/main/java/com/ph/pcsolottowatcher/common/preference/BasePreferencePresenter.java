package com.ph.pcsolottowatcher.common.preference;

public class BasePreferencePresenter<V extends BasePreferenceView> {
  protected V view;

  public BasePreferencePresenter(V view) {
    this.view = view;
  }
}
