package com.ph.pcsolottowatcher;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.itsaky.androidide.logsender.LogSender;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.di.DaggerAppComponent;
import com.ph.pcsolottowatcher.di.modules.DataCoreModule;

public class StartApplication extends Application {
  private static AppComponent component;
  PrefHelper prefHelper;

  @Override
  public void onCreate() {
    super.onCreate();
    LogSender.startLogging(this);

    component =
        DaggerAppComponent.builder()
            .dataCoreModule(new DataCoreModule(getApplicationContext()))
            .build();

    prefHelper = component.getPrefHelper();

    setAppTheme(prefHelper.getString("pref_theme_key", "val_light"));
  }

  public void setAppTheme(String theme) {
    switch (theme) {
      case "val_light":
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        break;
      case "val_dark":
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        break;
      case "val_system":
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        break;
    }
  }

  public static AppComponent getComponent() {
    return component;
  }
}
