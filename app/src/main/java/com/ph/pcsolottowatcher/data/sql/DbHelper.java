package com.ph.pcsolottowatcher.data.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ph.pcsolottowatcher.data.sql.cache.DHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.OneTimeHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.SixDigitsHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.ThreeTimeHistoryCache;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.data.sql.search.SearchHistoryCache;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;

public abstract class DbHelper<T> extends SQLiteOpenHelper {
  protected static final String DATABASE_NAME = "PCSO.db";
  protected static final int DATABASE_VERSION = 3;

  private Context context;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(DHistoryCache.DHISTORY_QUERY);
    db.execSQL(SixDigitsHistoryCache.SIXDIGITSHISTORY_QUERY);
    db.execSQL(OneTimeHistoryCache.ONETIMEHISTORY_QUERY);
    db.execSQL(ThreeTimeHistoryCache.THREETIMEHISTORY_QUERY);
    db.execSQL(PostCache.POST_QUERY);
    db.execSQL(SearchHistoryCache.SEARCH_QUERY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    resetAllCaches(db);
  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    resetAllCaches(db);
  }

  public void resetAllCaches(SQLiteDatabase db) {
    db.execSQL("DROP TABLE IF EXISTS " + DHistoryModel.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + OneTimeHistoryModel.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + ThreeTimeHistoryModel.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + SixDigitsHistoryModel.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + PostCache.TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + SearchHistoryCache.TABLE);
    onCreate(db);
  }

  public void deleteCache(String row, String column) {
    SQLiteDatabase db = this.getWritableDatabase();
    String whereClause = column + " = ?";
    String[] whereArgs = new String[] {row};
    db.delete(getCacheName(), whereClause, whereArgs);
  }

  public boolean isCached(String row, String column) {
    if (!isTableEmpty()) {
      return isItemExisted(row, column);
    }
    return false;
  }

  public boolean isTableEmpty() {
    String countQuery = "SELECT COUNT(*) FROM " + getCacheName();
    SQLiteDatabase db = this.getReadableDatabase();

    try (Cursor cursor = db.rawQuery(countQuery, null)) {
      cursor.moveToFirst();
      int count = cursor.getInt(0);
      return count == 0;
    }
  }

  private boolean isItemExisted(String row, String column) {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT * FROM " + getCacheName() + " WHERE " + column + " = ?";
    String[] selectionArgs = {row};

    try (Cursor cursor = db.rawQuery(query, selectionArgs)) {
      int count = cursor.getCount();
      return count > 0;
    }
  }

  protected abstract String getCacheName();

  protected abstract String getCacheQuery();

  public abstract void resetCache();
}
