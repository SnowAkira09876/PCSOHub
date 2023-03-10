package com.ph.pcsolottowatcher.data.sql.search;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ph.pcsolottowatcher.data.sql.DbHelper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchHistoryCache extends DbHelper {
  public static final String SEARCH_COLUMN = "post", TABLE = "Search";
  public static final String SEARCH_QUERY =
      "CREATE TABLE " + TABLE + " (" + "id" + " INTEGER PRIMARY KEY," + SEARCH_COLUMN + " TEXT)";

  @Inject
  public SearchHistoryCache(Context context) {
    super(context);
  }

  public void addSearch(String search) {
    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(SEARCH_COLUMN, search);
      db.insert(TABLE, null, values);
    }
  }

  public List<String> getSearchHistory() {
    List<String> list = new ArrayList<>();

    String selectQuery = "SELECT * FROM " + getCacheName();

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          String id = cursor.getString(cursor.getColumnIndexOrThrow(SEARCH_COLUMN));
          list.add(id);

        } while (cursor.moveToNext());
      }
    }

    return list;
  }

  @Override
  public void resetCache() {
    getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + getCacheName());
    getWritableDatabase().execSQL(getCacheQuery());
  }

  @Override
  protected String getCacheName() {
    return TABLE;
  }

  @Override
  protected String getCacheQuery() {
    return SEARCH_QUERY;
  }
}
