package com.ph.pcsolottowatcher.pojos.threetime;

import android.os.Parcel;
import android.os.Parcelable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;

public class ThreeTimeHistoryModel extends BaseHistoryModel implements Parcelable {
  private String timeOne, timeTwo, timeThree, timeOneResult, timeTwoResult, timeThreeResult;
  public static final String TABLE_NAME = "ThreeTimeHistory";

  public static final String COLUMN_TIME_ONE = "timeOne";
  public static final String COLUMN_TIME_TWO = "timeTwo";
  public static final String COLUMN_TIME_THREE = "timeThree";

  public static final String COLUMN_TIME_ONE_RESULT = "timeOneResult";
  public static final String COLUMN_TIME_TWO_RESULT = "timeTwoResult";
  public static final String COLUMN_TIME_THREE_RESULT = "timeThreeResult";

  public ThreeTimeHistoryModel() {}

  public String getTimeOne() {
    return this.timeOne;
  }

  public void setTimeOne(java.lang.String timeOne) {
    this.timeOne = timeOne;
  }

  public String getTimeTwo() {
    return this.timeTwo;
  }

  public void setTimeTwo(java.lang.String timeTwo) {
    this.timeTwo = timeTwo;
  }

  public String getTimeThree() {
    return this.timeThree;
  }

  public void setTimeThree(java.lang.String timeThree) {
    this.timeThree = timeThree;
  }

  public String getTimeOneResult() {
    return this.timeOneResult;
  }

  public void setTimeOneResult(String timeOneResult) {
    this.timeOneResult = timeOneResult;
  }

  public String getTimeTwoResult() {
    return this.timeTwoResult;
  }

  public void setTimeTwoResult(String timeTwoResult) {
    this.timeTwoResult = timeTwoResult;
  }

  public String getTimeThreeResult() {
    return this.timeThreeResult;
  }

  public void setTimeThreeResult(String timeThreeResult) {
    this.timeThreeResult = timeThreeResult;
  }

  // [PARCELABLE]
  private ThreeTimeHistoryModel(Parcel parcel) {
    this.date = parcel.readString();
    this.timeOne = parcel.readString();
    this.timeTwo = parcel.readString();
    this.timeThree = parcel.readString();
    this.timeOneResult = parcel.readString();
    this.timeTwoResult = parcel.readString();
    this.timeThreeResult = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(date);
    parcel.writeString(timeOne);
    parcel.writeString(timeTwo);
    parcel.writeString(timeThree);
    parcel.writeString(timeOneResult);
    parcel.writeString(timeTwoResult);
    parcel.writeString(timeThreeResult);
  }

  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator<ThreeTimeHistoryModel>() {
        @Override
        public ThreeTimeHistoryModel createFromParcel(Parcel parcel) {
          return new ThreeTimeHistoryModel(parcel);
        }

        @Override
        public ThreeTimeHistoryModel[] newArray(int i) {
          return new ThreeTimeHistoryModel[i];
        }
      };

  // [PARCELABLE]
}
