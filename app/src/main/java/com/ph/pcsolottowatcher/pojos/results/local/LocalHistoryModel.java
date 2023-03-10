package com.ph.pcsolottowatcher.pojos.results.local;

import android.os.Parcel;
import android.os.Parcelable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;

public class LocalHistoryModel extends BaseHistoryModel implements Parcelable {
  private String number, prize, winners;

  public LocalHistoryModel() {}

  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getPrize() {
    return this.prize;
  }

  public void setPrize(String prize) {
    this.prize = prize;
  }

  public String getWinners() {
    return this.winners;
  }

  public void setWinners(String winners) {
    this.winners = winners;
  }

  // [PARCELABLE]
  private LocalHistoryModel(Parcel parcel) {
    this.name = parcel.readString();
    this.date = parcel.readString();
    this.number = parcel.readString();
    this.prize = parcel.readString();
    this.winners = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeString(date);
    parcel.writeString(number);
    parcel.writeString(prize);
    parcel.writeString(winners);
  }

  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator<LocalHistoryModel>() {
        @Override
        public LocalHistoryModel createFromParcel(Parcel parcel) {
          return new LocalHistoryModel(parcel);
        }

        @Override
        public LocalHistoryModel[] newArray(int i) {
          return new LocalHistoryModel[i];
        }
      };

  // [PARCELABLE]
}
