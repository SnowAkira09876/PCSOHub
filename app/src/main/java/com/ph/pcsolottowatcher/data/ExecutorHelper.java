package com.ph.pcsolottowatcher.data;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutorHelper {
  private ExecutorService executor;

  @Inject
  public ExecutorHelper() {
    this.executor = Executors.newSingleThreadExecutor();
  }

  public void execute(Runnable runnable) {
    executor.execute(runnable);
  }

  public <T> Future<ArrayList<T>> submit(Callable<ArrayList<T>> callable) {
    return executor.submit(callable);
  }
}
