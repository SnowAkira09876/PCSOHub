package com.ph.pcsolottowatcher.viewmodels;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainSettingsFragmentViewModel extends ViewModel {
  private final MutableLiveData<Pair<String, String>> account = new MutableLiveData<>();

  public void setAccount(Pair<String, String> account) {
    this.account.setValue(account);
  }

  public LiveData<Pair<String, String>> getAccount() {
    return this.account;
  }
}
