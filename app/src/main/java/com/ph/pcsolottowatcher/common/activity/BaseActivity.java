package com.ph.pcsolottowatcher.common.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.android.material.color.DynamicColors;

public abstract class BaseActivity<Presenter extends BaseActivityPresenter>
    extends AppCompatActivity implements BaseActivityView {

  protected Presenter presenter;

  @NonNull protected abstract Presenter createPresenter();

  protected abstract void injectDependencies();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();

    SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
    presenter = createPresenter();
    presenter.applyDynamicTheme();
  }

  @Override
  public void applyDynamicTheme(boolean apply) {
    if (apply) DynamicColors.applyToActivityIfAvailable(this);
  }
}
