package com.ph.pcsolottowatcher.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;

public class AllResultsViewModel extends ViewModel {
  private final MutableLiveData<LottoGameBaseModel> model = new MutableLiveData<>();
  private final MutableLiveData<String> search = new MutableLiveData<>();
  private final MutableLiveData<String> date = new MutableLiveData<>();
  private final MutableLiveData<String> year = new MutableLiveData<>();
  private final MutableLiveData<Boolean> place_holder = new MutableLiveData<>();
  private final MutableLiveData<Boolean> jsonBusy = new MutableLiveData<>();

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

  public void setDate(String date) {
    this.date.setValue(date);
  }

  public LiveData<String> getDate() {
    return date;
  }

  public void setYear(String year) {
    this.year.setValue(year);
  }

  public LiveData<String> getYear() {
    return year;
  }

  public void setPlaceHolder(Boolean show) {
    this.place_holder.setValue(show);
  }

  public LiveData<Boolean> getPlaceHolder() {
    return place_holder;
  }

  public void setJsonBusy(Boolean busy) {
    this.jsonBusy.setValue(busy);
  }

  public LiveData<Boolean> getJsonBusy() {
    return jsonBusy;
  }

  public void reset() {
    this.date.setValue("");
    this.year.setValue("");
  }
}
