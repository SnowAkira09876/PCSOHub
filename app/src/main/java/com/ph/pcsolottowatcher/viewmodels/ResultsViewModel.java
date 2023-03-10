package com.ph.pcsolottowatcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;

public class ResultsViewModel extends ViewModel {
  private final MutableLiveData<LottoGameBaseModel> model = new MutableLiveData<>();
  private final MutableLiveData<String> search = new MutableLiveData<>();
  private final MutableLiveData<Boolean> place_holder = new MutableLiveData<>();
  private final MutableLiveData<Boolean> jsoupBusy = new MutableLiveData<>();

  public void setModel(LottoGameBaseModel model) {
    this.model.setValue(model);
  }

  public LiveData<LottoGameBaseModel> getModel() {
    return model;
  }

  public void setSearch(String sText) {
    search.setValue(sText);
  }

  public LiveData<String> getSearch() {
    return search;
  }

  public void setPlaceHolder(Boolean show) {
    this.place_holder.setValue(show);
  }

  public LiveData<Boolean> getPlaceHolder() {
    return place_holder;
  }

  public void setJsoupBusy(Boolean busy) {
    this.jsoupBusy.setValue(busy);
  }

  public LiveData<Boolean> getJsoupBusy() {
    return jsoupBusy;
  }
}
