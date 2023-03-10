package com.ph.pcsolottowatcher.data.sql.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DHistoryCache extends BaseCache<DHistoryModel> {
  public static final String DHISTORY_QUERY =
      "CREATE TABLE "
          + DHistoryModel.TABLE_NAME
          + " ("
          + BaseHistoryModel.COLUMN_ID
          + " INTEGER PRIMARY KEY,"
          + BaseHistoryModel.COLUMN_NAME
          + " TEXT,"
          + BaseHistoryModel.COLUMN_DATE
          + " TEXT,"
          + DHistoryModel.COLUMN_NUMBER
          + " TEXT,"
          + BaseHistoryModel.TIME_STAMP
          + " TEXT)";

  @Inject
  public DHistoryCache(Context context, ExecutorHelper executor) {
    super(context, executor);
  }

  @Override
  public void addHistory(DHistoryModel model) {
    Calendar calendar = Calendar.getInstance();
    String timeStamp = dateFormat.format(calendar.getTime());

    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(DHistoryModel.COLUMN_NAME, model.getName());
      values.put(DHistoryModel.COLUMN_DATE, model.getDate());
      values.put(DHistoryModel.COLUMN_NUMBER, model.getNumber());
      values.put(BaseHistoryModel.TIME_STAMP, timeStamp);

      db.insert(getCacheName(), null, values);
    }
  }

  @Override
  protected ArrayList<DHistoryModel> getHistory(String name) {
    list.clear();

    String selectQuery = "SELECT * FROM " + getCacheName();

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          DHistoryModel model = new DHistoryModel();
          model.setName(cursor.getString(cursor.getColumnIndexOrThrow(DHistoryModel.COLUMN_NAME)));
          model.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DHistoryModel.COLUMN_DATE)));
          model.setNumber(
              cursor.getString(cursor.getColumnIndexOrThrow(DHistoryModel.COLUMN_NUMBER)));

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

  @Override
  protected String getCacheName() {
    return DHistoryModel.TABLE_NAME;
  }

  @Override
  protected String getCacheQuery() {
    return DHISTORY_QUERY;
  }
}
