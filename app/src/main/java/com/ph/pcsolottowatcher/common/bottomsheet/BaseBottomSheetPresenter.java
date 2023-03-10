package com.ph.pcsolottowatcher.common.bottomsheet;

public class BaseBottomSheetPresenter<V extends BaseBottomSheetView> {

  private V view;

  public BaseBottomSheetPresenter(V view) {
    this.view = view;
  }
}
