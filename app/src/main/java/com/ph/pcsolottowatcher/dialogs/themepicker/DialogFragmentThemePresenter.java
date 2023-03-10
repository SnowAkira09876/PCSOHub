package com.ph.pcsolottowatcher.dialogs.themepicker;

import com.ph.pcsolottowatcher.common.dialog.BaseDialogPresenter;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import java.lang.ref.WeakReference;

public class DialogFragmentThemePresenter extends BaseDialogPresenter<DialogFragmentThemeView> {
  private ThemeRepository repo;

  public DialogFragmentThemePresenter(DialogFragmentThemeView view, PrefHelper prefHelper) {
    super(view);
    this.repo = new ThemeRepository(view, prefHelper);
  }

  public void setTheme(String key, String theme) {
    repo.setTheme(key, theme);
  }

  public void onCurrentTheme() {
    repo.onCurrentTheme();
  }

  public static class ThemeRepository {
    private WeakReference<DialogFragmentThemeView> view;
    private PrefHelper prefHelper;

    public ThemeRepository(DialogFragmentThemeView view, PrefHelper prefHelper) {
      this.view = new WeakReference<>(view);
      this.prefHelper = prefHelper;
    }

    public void setTheme(String key, String theme) {
      prefHelper.setString(key, theme);
      view.get().onThemeChanged(theme);
    }

    public void onCurrentTheme() {
      String theme = prefHelper.getString("pref_theme_key", "val_light");

      if ("val_light".equals(theme)) view.get().onCurrentTheme(0);
      else if ("val_dark".equals(theme)) view.get().onCurrentTheme(1);
      else if ("val_system".equals(theme)) view.get().onCurrentTheme(2);
    }
  }
}
