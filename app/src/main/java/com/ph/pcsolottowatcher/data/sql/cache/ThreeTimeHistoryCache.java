package com.ph.pcsolottowatcher.data.sql.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ThreeTimeHistoryCache extends BaseCache<ThreeTimeHistoryModel> {
  public static final String THREETIMEHISTORY_QUERY =
      "CREATE TABLE "
          + ThreeTimeHistoryModel.TABLE_NAME
          + " ("
          + BaseHistoryModel.COLUMN_ID
          + " INTEGER PRIMARY KEY,"
          + BaseHistoryModel.COLUMN_NAME
          + " TEXT,"
          + BaseHistoryModel.COLUMN_DATE
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_ONE
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_TWO
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_THREE
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_ONE_RESULT
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_TWO_RESULT
          + " TEXT,"
          + ThreeTimeHistoryModel.COLUMN_TIME_THREE_RESULT
          + " TEXT,"
          + BaseHistoryModel.TIME_STAMP
          + " TEXT)";

  @Inject
  public ThreeTimeHistoryCache(Context context, ExecutorHelper executor) {
    super(context, executor);
  }

  @Override
  public void addHistory(ThreeTimeHistoryModel model) {
    Calendar calendar = Calendar.getInstance();
    String timeStamp = dateFormat.format(calendar.getTime());

    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(ThreeTimeHistoryModel.COLUMN_NAME, model.getName());
      values.put(ThreeTimeHistoryModel.COLUMN_DATE, model.getDate());

      values.put(ThreeTimeHistoryModel.COLUMN_TIME_ONE, model.getTimeOne());
      values.put(ThreeTimeHistoryModel.COLUMN_TIME_TWO, model.getTimeTwo());
      values.put(ThreeTimeHistoryModel.COLUMN_TIME_THREE, model.getTimeThree());

      values.put(ThreeTimeHistoryModel.COLUMN_TIME_ONE_RESULT, model.getTimeOneResult());
      values.put(ThreeTimeHistoryModel.COLUMN_TIME_TWO_RESULT, model.getTimeTwoResult());
      values.put(ThreeTimeHistoryModel.COLUMN_TIME_THREE_RESULT, model.getTimeThreeResult());

      values.put(BaseHistoryModel.TIME_STAMP, timeStamp);
      db.insert(getCacheName(), null, values);
    }
  }

  @Override
  protected ArrayList<ThreeTimeHistoryModel> getHistory(String name) {
    list.clear();

    String selectQuery = "SELECT * FROM " + getCacheName();

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          ThreeTimeHistoryModel model = new ThreeTimeHistoryModel();
          model.setName(
              cursor.getString(cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_NAME)));
          model.setDate(
              cursor.getString(cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_DATE)));

          model.setTimeOne(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_ONE)));
          model.setTimeTwo(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_TWO)));
          model.setTimeThree(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_THREE)));

          model.setTimeOneResult(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_ONE_RESULT)));
          model.setTimeTwoResult(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_TWO_RESULT)));
          model.setTimeThreeResult(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(ThreeTimeHistoryModel.COLUMN_TIME_THREE_RESULT)));

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
    return ThreeTimeHistoryModel.TABLE_NAME;
  }

  @Override
  protected String getCacheQuery() {
    return THREETIMEHISTORY_QUERY;
  }
}
