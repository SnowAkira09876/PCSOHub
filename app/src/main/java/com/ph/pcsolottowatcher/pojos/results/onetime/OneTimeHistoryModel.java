package com.ph.pcsolottowatcher.pojos.onetime;

import android.os.Parcel;
import android.os.Parcelable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;

public class OneTimeHistoryModel extends BaseHistoryModel implements Parcelable {
  private String timeOne, timeOneResult;
  public static final String TABLE_NAME = "OneTimeHistory";
  public static final String COLUMN_TIMEONE = "timeOne";
  public static final String COLUMN_TIMEONE_RESULT = "timeOneResult";

  public OneTimeHistoryModel() {}

  public String getTimeOne() {
    return this.timeOne;
  }

  public void setTimeOne(java.lang.String timeOne) {
    this.timeOne = timeOne;
  }

  public String getTimeOneResult() {
    return this.timeOneResult;
  }

  public void setTimeOneResult(String timeOneResult) {
    this.timeOneResult = timeOneResult;
  }

  // [PARCELABLE]
  private OneTimeHistoryModel(Parcel parcel) {
    this.date = parcel.readString();
    this.timeOne = parcel.readString();
    this.timeOneResult = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(date);
    parcel.writeString(timeOne);
    parcel.writeString(timeOneResult);
  }

  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator<OneTimeHistoryModel>() {
        @Override
        public OneTimeHistoryModel createFromParcel(Parcel parcel) {
          return new OneTimeHistoryModel(parcel);
        }

        @Override
        public OneTimeHistoryModel[] newArray(int i) {
          return new OneTimeHistoryModel[i];
        }
      };

  // [PARCELABLE]
}
