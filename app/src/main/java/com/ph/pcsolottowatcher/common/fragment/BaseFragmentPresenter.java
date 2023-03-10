package com.ph.pcsolottowatcher.common.fragment;

public class BaseFragmentPresenter<V extends BaseFragmentView> {

  protected V view;

  protected BaseFragmentPresenter(V view) {
    this.view = view;
  }
}
