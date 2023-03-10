package com.ph.pcsolottowatcher.pojos.d;

import android.os.Parcel;
import android.os.Parcelable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;

public class DHistoryModel extends BaseHistoryModel implements Parcelable {
  private String number;
  public static final String TABLE_NAME = "DHistory";
  public static final String COLUMN_NUMBER = "number";

  public DHistoryModel() {}

  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  // [PARCELABLE]
  private DHistoryModel(Parcel parcel) {
    this.date = parcel.readString();
    this.number = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(date);
    parcel.writeString(number);
  }

  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator<DHistoryModel>() {
        @Override
        public DHistoryModel createFromParcel(Parcel parcel) {
          return new DHistoryModel(parcel);
        }

        @Override
        public DHistoryModel[] newArray(int i) {
          return new DHistoryModel[i];
        }
      };

  // [PARCELABLE]

}
