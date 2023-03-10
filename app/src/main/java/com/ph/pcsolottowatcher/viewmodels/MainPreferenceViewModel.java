package com.ph.pcsolottowatcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainPreferenceViewModel extends ViewModel {
  private final MutableLiveData<String> message = new MutableLiveData<>();
  private final MutableLiveData<Void> signin = new MutableLiveData<>();
  private final MutableLiveData<Void> image = new MutableLiveData<>();

  public void setMessage(String message) {
    this.message.setValue(message);
  }

  public LiveData<String> getMessage() {
    return this.message;
  }

  public void setSignIn() {
    signin.setValue(null);
  }

  public LiveData<Void> getSignIn() {
    return signin;
  }

  public void setProfile() {
    image.setValue(null);
  }

  public LiveData<Void> getProfile() {
    return image;
  }
}
