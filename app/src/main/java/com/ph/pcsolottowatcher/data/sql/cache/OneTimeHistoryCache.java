package com.ph.pcsolottowatcher.data.sql.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OneTimeHistoryCache extends BaseCache<OneTimeHistoryModel> {
  public static final String ONETIMEHISTORY_QUERY =
      "CREATE TABLE "
          + OneTimeHistoryModel.TABLE_NAME
          + " ("
          + BaseHistoryModel.COLUMN_ID
          + " INTEGER PRIMARY KEY,"
          + BaseHistoryModel.COLUMN_NAME
          + " TEXT,"
          + BaseHistoryModel.COLUMN_DATE
          + " TEXT,"
          + OneTimeHistoryModel.COLUMN_TIMEONE
          + " TEXT,"
          + OneTimeHistoryModel.COLUMN_TIMEONE_RESULT
          + " TEXT,"
          + BaseHistoryModel.TIME_STAMP
          + " TEXT)";

  @Inject
  public OneTimeHistoryCache(Context context, ExecutorHelper executor) {
    super(context, executor);
  }

  @Override
  public void addHistory(OneTimeHistoryModel model) {
    Calendar calendar = Calendar.getInstance();
    String timeStamp = dateFormat.format(calendar.getTime());

    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(OneTimeHistoryModel.COLUMN_NAME, model.getName());
      values.put(OneTimeHistoryModel.COLUMN_DATE, model.getDate());
      values.put(OneTimeHistoryModel.COLUMN_TIMEONE, model.getTimeOne());
      values.put(OneTimeHistoryModel.COLUMN_TIMEONE_RESULT, model.getTimeOneResult());
      values.put(BaseHistoryModel.TIME_STAMP, timeStamp);

      db.insert(OneTimeHistoryModel.TABLE_NAME, null, values);
    }
  }

  @Override
  protected ArrayList<OneTimeHistoryModel> getHistory(String name) {
    list.clear();

    String selectQuery = "SELECT * FROM " + OneTimeHistoryModel.TABLE_NAME;

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          OneTimeHistoryModel model = new OneTimeHistoryModel();
          model.setName(
              cursor.getString(cursor.getColumnIndexOrThrow(OneTimeHistoryModel.COLUMN_NAME)));
          model.setDate(
              cursor.getString(cursor.getColumnIndexOrThrow(OneTimeHistoryModel.COLUMN_DATE)));
          model.setTimeOne(
              cursor.getString(cursor.getColumnIndexOrThrow(OneTimeHistoryModel.COLUMN_TIMEONE)));
          model.setTimeOneResult(
              cursor.getString(
                  cursor.getColumnIndexOrThrow(OneTimeHistoryModel.COLUMN_TIMEONE_RESULT)));

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
    return OneTimeHistoryModel.TABLE_NAME;
  }

  @Override
  protected String getCacheQuery() {
    return ONETIMEHISTORY_QUERY;
  }
}
