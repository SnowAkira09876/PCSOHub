package com.ph.pcsolottowatcher.data.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefHelper {
  private SharedPreferences sp;
  private SharedPreferences.Editor editor;

  @Inject
  public PrefHelper(Context context) {
    this.sp = PreferenceManager.getDefaultSharedPreferences(context);
    this.editor = sp.edit();
  }

  public void setString(String key, String string) {
    editor.putString(key, string);
    editor.apply();
  }

  public String getString(String key, String defaultVal) {
    return sp.getString(key, defaultVal);
  }

  public void setBoolean(String key, boolean val) {
    editor.putBoolean(key, val);
    editor.apply();
  }

  public boolean getBoolean(String key, boolean val) {
    return sp.getBoolean(key, val);
  }
}
