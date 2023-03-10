package com.ph.pcsolottowatcher.data.sql.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SixDigitsHistoryCache extends BaseCache<SixDigitsHistoryModel> {
  public static final String SIXDIGITSHISTORY_QUERY =
      "CREATE TABLE "
          + SixDigitsHistoryModel.TABLE_NAME
          + " ("
          + BaseHistoryModel.COLUMN_ID
          + " INTEGER PRIMARY KEY,"
          + BaseHistoryModel.COLUMN_NAME
          + " TEXT,"
          + BaseHistoryModel.COLUMN_DATE
          + " TEXT,"
          + SixDigitsHistoryModel.COLUMN_NUMBER
          + " TEXT,"
          + SixDigitsHistoryModel.COLUMN_PRIZE
          + " TEXT,"
          + SixDigitsHistoryModel.COLUMN_DATE_LINK
          + " TEXT,"
          + BaseHistoryModel.TIME_STAMP
          + " TEXT)";

  @Inject
  public SixDigitsHistoryCache(Context context, ExecutorHelper executor) {
    super(context, executor);
  }

  @Override
  public void addHistory(SixDigitsHistoryModel model) {
    Calendar calendar = Calendar.getInstance();
    String timeStamp = dateFormat.format(calendar.getTime());

    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(SixDigitsHistoryModel.COLUMN_NAME, model.getName());
      values.put(SixDigitsHistoryModel.COLUMN_DATE, model.getDate());
      values.put(SixDigitsHistoryModel.COLUMN_NUMBER, model.getNumber());
      values.put(SixDigitsHistoryModel.COLUMN_PRIZE, model.getPrize());
      values.put(BaseHistoryModel.TIME_STAMP, timeStamp);

      db.insert(getCacheName(), null, values);
    }
  }

  @Override
  protected ArrayList<SixDigitsHistoryModel> getHistory(String name) {
    list.clear();

    String selectQuery = "SELECT * FROM " + getCacheName();

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          SixDigitsHistoryModel model = new SixDigitsHistoryModel();
          model.setName(
              cursor.getString(cursor.getColumnIndexOrThrow(SixDigitsHistoryModel.COLUMN_NAME)));
          model.setDate(
              cursor.getString(cursor.getColumnIndexOrThrow(SixDigitsHistoryModel.COLUMN_DATE)));
          model.setNumber(
              cursor.getString(cursor.getColumnIndexOrThrow(SixDigitsHistoryModel.COLUMN_NUMBER)));
          model.setPrize(
              cursor.getString(cursor.getColumnIndexOrThrow(SixDigitsHistoryModel.COLUMN_PRIZE)));

          if (model.getName().equals(name)) {
            list.add(model);
            timeStamp_from_sqlite =
                cursor.getString(cursor.getColumnIndexOrThrow(BaseHistoryModel.TIME_STAMP));
          }
        } while (cursor.moveToNext());
      }
    }
    return list;
  }

  public String getCacheName() {
    return SixDigitsHistoryModel.TABLE_NAME;
  }

  @Override
  protected String getCacheQuery() {
    return SIXDIGITSHISTORY_QUERY;
  }
}
