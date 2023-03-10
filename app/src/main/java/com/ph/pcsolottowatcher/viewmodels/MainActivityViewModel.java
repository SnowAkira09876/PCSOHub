package com.ph.pcsolottowatcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
  private final MutableLiveData<LottoGameBaseModel> model = new MutableLiveData<>();
  private final MutableLiveData<String> search = new MutableLiveData<>();
  private final MutableLiveData<String> message = new MutableLiveData<>();
  private final MutableLiveData<Void> login = new MutableLiveData<>();
  private final MutableLiveData<Void> signin = new MutableLiveData<>();
  private final MutableLiveData<Void> profile_updated = new MutableLiveData<>();
  private final MutableLiveData<List<LottoGameBaseModel>> games = new MutableLiveData<>();
  private final MutableLiveData<Boolean> fab = new MutableLiveData<>();
  private final MutableLiveData<Integer> badge = new MutableLiveData<>();

  public void setModel(LottoGameBaseModel model) {
    this.model.setValue(model);
  }

  public LiveData<LottoGameBaseModel> getModel() {
    return model;
  }

  public void setSearch(String search) {
    this.search.setValue(search);
  }

  public LiveData<String> getSearch() {
    return search;
  }

  public void setDialogLogin() {
    login.setValue(null);
  }

  public LiveData<Void> getDialogLogin() {
    return login;
  }

  public void setSignIn() {
    signin.setValue(null);
  }

  public LiveData<Void> getSignIn() {
    return signin;
  }

  public void setOnProfileUpdated() {
    profile_updated.setValue(null);
  }

  public LiveData<Void> getOnProfileUpdated() {
    return profile_updated;
  }

  public void setGames(List<LottoGameBaseModel> games) {
    this.games.setValue(games);
  }

  public LiveData<List<LottoGameBaseModel>> getGames() {
    return this.games;
  }

  public void setMessage(String message) {
    this.message.setValue(message);
  }

  public LiveData<String> getMessage() {
    return this.message;
  }

  public void setFab(boolean show) {
    this.fab.setValue(show);
  }

  public LiveData<Boolean> getFab() {
    return fab;
  }

  public void setBadge(int badge) {
    this.badge.setValue(badge);
  }

  public LiveData<Integer> getBadge() {
    return badge;
  }
}
