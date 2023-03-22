package com.ph.pcsolottowatcher.data.jsoup;

import android.os.Handler;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import com.ph.pcsolottowatcher.data.sql.cache.BaseCache;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class BaseParser<T extends BaseHistoryModel> implements Runnable {
  protected String name, link;
  protected int tableNumber;
  protected BaseCache<T> baseCache;
  protected Document history;
  protected Handler handler;
  protected final ArrayList<T> list = new ArrayList<>();
  protected ParserListener<T> listener;
  protected String userAgent;

  public BaseParser(Handler handler, WebView webView) {
    this.handler = handler;
    this.userAgent = webView.getSettings().getUserAgentString();
  }

  @Override
  public void run() {
    try {
      this.history = Jsoup.connect(link).timeout(4000).userAgent(userAgent).get();

      handler.post(
          () -> {
            listener.onGettingData(getHistory());
          });

    } catch (IOException | NullPointerException | IndexOutOfBoundsException e) {
      handler.post(
          () -> {
            if (e instanceof java.net.UnknownHostException)
              listener.onError(
                  "Can\'t connect to server. Please check your internet and try again. ");
            else
              listener.onError(
                  "Something went wrong with your request. Please report this incident to the developer.");
          });
    }
  }

  protected abstract ArrayList<T> getHistory();

  public String getName() {
    return this.name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(@NonNull String link) {
    this.link = link;
  }

  public int getTableNumber() {
    return this.tableNumber;
  }

  public void setTableNumber(@NonNull int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public BaseCache<T> getBaseCache() {
    return this.baseCache;
  }

  public void setBaseCache(@NonNull BaseCache<T> baseCache) {
    this.baseCache = baseCache;
  }

  public ParserListener<T> getListener() {
    return this.listener;
  }

  public void setListener(@NonNull ParserListener<T> listener) {
    this.listener = listener;
  }

  public interface ParserListener<T> {
    void onGettingData(ArrayList<T> list);

    void onError(String message);
  }
}
