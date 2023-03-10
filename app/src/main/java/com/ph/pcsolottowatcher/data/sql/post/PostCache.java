package com.ph.pcsolottowatcher.data.sql.post;

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
public class PostCache extends DbHelper {
  public static final String POST = "post", TABLE = "Posts";
  public static final String POST_QUERY =
      "CREATE TABLE " + TABLE + " (" + "id" + " INTEGER PRIMARY KEY," + POST + " TEXT)";

  @Inject
  public PostCache(Context context) {
    super(context);
  }

  public void addPostId(String id) {
    try (SQLiteDatabase db = this.getWritableDatabase()) {
      ContentValues values = new ContentValues();
      values.put(POST, id);
      db.insert(TABLE, null, values);
    }
  }

  public int getSeenPostsSize() {
    List<String> list = new ArrayList<>();

    String selectQuery = "SELECT * FROM " + getCacheName();

    try (SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null)) {

      if (cursor.moveToFirst()) {
        do {
          String id = cursor.getString(cursor.getColumnIndexOrThrow(POST));
          list.add(id);

        } while (cursor.moveToNext());
      }
    }

    return list.size();
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
    return POST_QUERY;
  }
}
