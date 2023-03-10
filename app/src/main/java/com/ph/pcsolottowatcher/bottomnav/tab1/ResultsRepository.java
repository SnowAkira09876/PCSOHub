package com.ph.pcsolottowatcher.bottomnav.tab1;

import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.data.jsoup.BaseParser;
import com.ph.pcsolottowatcher.data.sql.cache.BaseCache;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ResultsRepository {
  private WeakReference<ResultsView> view;
  private JsonHelper jsonHelper;
  private BaseParser<SixDigitsHistoryModel> sixDigitsParser;
  private BaseParser<DHistoryModel> dParser;
  private BaseParser<OneTimeHistoryModel> oneTimeParser;
  private BaseParser<ThreeTimeHistoryModel> threeTimeParser;

  private BaseCache<DHistoryModel> dHistoryCache;
  private BaseCache<SixDigitsHistoryModel> sixDigitsHistoryCache;
  private BaseCache<OneTimeHistoryModel> oneTimeHistoryCache;
  private BaseCache<ThreeTimeHistoryModel> threeTimeHistoryCache;
  private ExecutorHelper executor;

  public ResultsRepository(
      ResultsView view,
      JsonHelper jsonHelper,
      BaseCache<DHistoryModel> dHistoryCache,
      BaseCache<SixDigitsHistoryModel> sixDigitsHistoryCache,
      BaseCache<OneTimeHistoryModel> oneTimeHistoryCache,
      BaseCache<ThreeTimeHistoryModel> threeTimeHistoryCache,
      ExecutorHelper executor,
      BaseParser<SixDigitsHistoryModel> sixDigitsParser,
      BaseParser<DHistoryModel> dParser,
      BaseParser<OneTimeHistoryModel> oneTimeParser,
      BaseParser<ThreeTimeHistoryModel> threeTimeParser) {
    this.view = new WeakReference<>(view);
    this.jsonHelper = jsonHelper;
    this.dHistoryCache = dHistoryCache;
    this.sixDigitsHistoryCache = sixDigitsHistoryCache;
    this.oneTimeHistoryCache = oneTimeHistoryCache;
    this.threeTimeHistoryCache = threeTimeHistoryCache;
    this.executor = executor;
    this.sixDigitsParser = sixDigitsParser;
    this.dParser = dParser;
    this.oneTimeParser = oneTimeParser;
    this.threeTimeParser = threeTimeParser;
  }

  public void getGames() {
    Consumer<List<LottoGameBaseModel>> consumer =
        list -> {
          view.get().getGames(list);
        };
    jsonHelper.getLottoGamesList(consumer, JsonHelper.LOTTO_ARRAY_NAME);
  }

  public void getSixDigits(LottoGameBaseModel model) {
    BaseParser.ParserListener<SixDigitsHistoryModel> listener =
        new BaseParser.ParserListener<>() {

          @Override
          public void onGettingData(ArrayList<SixDigitsHistoryModel> list) {
            onView(list, view.get()::getSixDigitHistory);
          }

          @Override
          public void onError(String message) {
            onViewError(message);
          }
        };

    sixDigitsParser.setName(model.getName());
    sixDigitsParser.setLink(model.getHistoryLink());
    sixDigitsParser.setTableNumber(model.getTableNumber());
    sixDigitsParser.setListener(listener);
    sixDigitsParser.setBaseCache(sixDigitsHistoryCache);

    view.get().showProgress(true);
    executor.execute(sixDigitsParser);
  }

  public void getD(LottoGameBaseModel model) {
    BaseParser.ParserListener<DHistoryModel> listener =
        new BaseParser.ParserListener<>() {

          @Override
          public void onGettingData(ArrayList<DHistoryModel> list) {
            onView(list, view.get()::getDHistory);
          }

          @Override
          public void onError(String message) {
            onViewError(message);
          }
        };

    dParser.setName(model.getName());
    dParser.setLink(model.getHistoryLink());
    dParser.setTableNumber(model.getTableNumber());
    dParser.setListener(listener);
    dParser.setBaseCache(dHistoryCache);

    view.get().showProgress(true);
    executor.execute(dParser);
  }

  public void getThreeTime(LottoGameBaseModel model) {
    BaseParser.ParserListener<ThreeTimeHistoryModel> listener =
        new BaseParser.ParserListener<>() {

          @Override
          public void onGettingData(ArrayList<ThreeTimeHistoryModel> list) {
            onView(list, view.get()::getThreeTimeHistory);
          }

          @Override
          public void onError(String message) {
            onViewError(message);
          }
        };

    threeTimeParser.setName(model.getName());
    threeTimeParser.setLink(model.getHistoryLink());
    threeTimeParser.setTableNumber(model.getTableNumber());
    threeTimeParser.setListener(listener);
    threeTimeParser.setBaseCache(threeTimeHistoryCache);

    view.get().showProgress(true);
    executor.execute(threeTimeParser);
  }

  public void getOneTime(LottoGameBaseModel model) {
    BaseParser.ParserListener<OneTimeHistoryModel> listener =
        new BaseParser.ParserListener<>() {

          @Override
          public void onGettingData(ArrayList<OneTimeHistoryModel> list) {
            onView(list, view.get()::getOneTimeHistory);
          }

          @Override
          public void onError(String message) {
            onViewError(message);
          }
        };

    oneTimeParser.setName(model.getName());
    oneTimeParser.setLink(model.getHistoryLink());
    oneTimeParser.setTableNumber(model.getTableNumber());
    oneTimeParser.setListener(listener);
    oneTimeParser.setBaseCache(oneTimeHistoryCache);

    view.get().showProgress(true);
    executor.execute(oneTimeParser);
  }

  public void getCachedDHistory(String name) {
    BiConsumer<ArrayList<DHistoryModel>, String> consumer =
        (list, timeStamp) -> {
          onViewCache(list, timeStamp, view.get()::getDHistory);
        };

    dHistoryCache.getCachedHistory(name, consumer);
  }

  public void getCachedSixDigitsHistory(String name) {
    BiConsumer<ArrayList<SixDigitsHistoryModel>, String> consumer =
        (list, timeStamp) -> {
          onViewCache(list, timeStamp, view.get()::getSixDigitHistory);
        };

    sixDigitsHistoryCache.getCachedHistory(name, consumer);
  }

  public void getCachedThreeTimeHistory(String name) {
    BiConsumer<ArrayList<ThreeTimeHistoryModel>, String> consumer =
        (list, timeStamp) -> {
          onViewCache(list, timeStamp, view.get()::getThreeTimeHistory);
        };

    threeTimeHistoryCache.getCachedHistory(name, consumer);
  }

  public void getCachedOneTimeHistory(String name) {
    BiConsumer<ArrayList<OneTimeHistoryModel>, String> consumer =
        (list, timeStamp) -> {
          onViewCache(list, timeStamp, view.get()::getOneTimeHistory);
        };

    oneTimeHistoryCache.getCachedHistory(name, consumer);
  }

  private <T extends BaseHistoryModel> void onView(
      ArrayList<T> list, Consumer<ArrayList<T>> view_method) {
    view_method.accept(list);

    view.get().onLastUpdatedTime("Updated");
    view.get().showProgress(false);
    view.get().onBusyParsing(false);
  }

  private <T extends BaseHistoryModel> void onViewCache(
      ArrayList<T> list, String timeStamp, Consumer<ArrayList<T>> view_method) {
    view_method.accept(list);
    view.get().onLastUpdatedTime(timeStamp);
    view.get().onBusyParsing(false);
  }

  private void onViewError(String error) {
    view.get().showError(error);
    view.get().showProgress(false);
    view.get().onBusyParsing(false);
  }
}
