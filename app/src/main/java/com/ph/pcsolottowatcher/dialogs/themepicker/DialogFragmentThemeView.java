package com.ph.pcsolottowatcher.dialogs.themepicker;

import com.ph.pcsolottowatcher.common.dialog.BaseDialogView;

public interface DialogFragmentThemeView extends BaseDialogView {

  void onThemeChanged(String theme);

  void onCurrentTheme(int selected);
}
