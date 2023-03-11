package com.ph.pcsolottowatcher.di.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DataCoreModule {
  private final Context context;

  public DataCoreModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  public Handler provideHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides
  @Singleton
  public Gson provideGson() {
    return new Gson();
  }

  @Provides
  @Singleton
  public WebView provideWebView() {
    return new WebView(context);
  }
}
