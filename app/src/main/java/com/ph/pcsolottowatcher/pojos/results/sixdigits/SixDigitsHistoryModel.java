package com.ph.pcsolottowatcher.pojos.sixdigits;

import android.os.Parcel;
import android.os.Parcelable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;

public class SixDigitsHistoryModel extends BaseHistoryModel implements Parcelable {
  private String number, prize;
  public static final String TABLE_NAME = "SixDigitsHistory";
  public static final String COLUMN_NUMBER = "number";
  public static final String COLUMN_PRIZE = "prize";
  public static final String COLUMN_DATE_LINK = "dateLink";
  public static final String COLUMN_WINNERS = "winners";

  public SixDigitsHistoryModel() {}

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

  // [PARCELABLE]
  private SixDigitsHistoryModel(Parcel parcel) {
    this.date = parcel.readString();
    this.number = parcel.readString();
    this.prize = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(date);
    parcel.writeString(number);
    parcel.writeString(prize);
  }

  public static final Parcelable.Creator CREATOR =
      new Parcelable.Creator<SixDigitsHistoryModel>() {
        @Override
        public SixDigitsHistoryModel createFromParcel(Parcel parcel) {
          return new SixDigitsHistoryModel(parcel);
        }

        @Override
        public SixDigitsHistoryModel[] newArray(int i) {
          return new SixDigitsHistoryModel[i];
        }
      };

  // [PARCELABLE]
}
