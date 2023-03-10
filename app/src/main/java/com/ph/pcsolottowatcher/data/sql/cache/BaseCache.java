package com.ph.pcsolottowatcher.data.sql.cache;

import android.content.Context;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.data.sql.DbHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

public abstract class BaseCache<T> extends DbHelper {
  protected static final String DATABASE_NAME = "PCSO.db";
  protected static final int DATABASE_VERSION = 1;
  protected final ArrayList<T> list = new ArrayList<>();
  protected String tableName, query;
  protected Context context;
  protected SimpleDateFormat dateFormat =
      new SimpleDateFormat("'Last updated on' MMM dd hh:mm a", Locale.US);
  protected String timeStamp_from_sqlite;
  public static final String NAME = "name";
  private ExecutorHelper executor;

  public BaseCache(Context context, ExecutorHelper executor) {
    super(context);
    this.context = context;
    this.executor = executor;
  }

  public abstract void addHistory(T dHistoryModel);

  protected abstract ArrayList<T> getHistory(String name);

  public void getCachedHistory(String name, BiConsumer<ArrayList<T>, String> consumer) {
    Future<ArrayList<T>> future =
        executor.submit(
            new Callable<ArrayList<T>>() {
              @Override
              public ArrayList<T> call() {
                return getHistory(name);
              }
            });

    try {
      ArrayList<T> result = future.get();
      consumer.accept(result, timeStamp_from_sqlite);
    } catch (InterruptedException | ExecutionException e) {
    }
  }

  @Override
  public void resetCache() {
    getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + getCacheName());
    getWritableDatabase().execSQL(getCacheQuery());
  }
}
