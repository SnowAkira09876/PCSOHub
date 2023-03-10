package com.ph.pcsolottowatcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;

public class FeedViewModel extends ViewModel {
  private final MutableLiveData<String> search = new MutableLiveData<>();
  private final MutableLiveData<LottoGameBaseModel> model = new MutableLiveData<>();
  private final MutableLiveData<Boolean> place_holder = new MutableLiveData<>();

  public void setSearch(String sText) {
    search.setValue(sText);
  }

  public LiveData<String> getSearch() {
    return search;
  }

  public void setModel(LottoGameBaseModel model) {
    this.model.setValue(model);
  }

  public LiveData<LottoGameBaseModel> getModel() {
    return model;
  }

  public void setPlaceHolder(Boolean show) {
    this.place_holder.setValue(show);
  }

  public LiveData<Boolean> getPlaceHolder() {
    return place_holder;
  }
}
