package com.ph.pcsolottowatcher.bottomnav.tab1;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentPresenter;
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
import java.util.function.Consumer;

public class ResultsPresenter extends BaseFragmentPresenter<ResultsView> {
  private ResultsRepository repo;
  private BaseCache<DHistoryModel> dHistoryCache;
  private BaseCache<SixDigitsHistoryModel> sixDigitsHistoryCache;
  private BaseCache<OneTimeHistoryModel> oneTimeHistoryCache;
  private BaseCache<ThreeTimeHistoryModel> threeTimeHistoryCache;

  public ResultsPresenter(
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
    super(view);
    this.dHistoryCache = dHistoryCache;
    this.sixDigitsHistoryCache = sixDigitsHistoryCache;
    this.oneTimeHistoryCache = oneTimeHistoryCache;
    this.threeTimeHistoryCache = threeTimeHistoryCache;
    this.repo =
        new ResultsRepository(
            view,
            jsonHelper,
            dHistoryCache,
            sixDigitsHistoryCache,
            oneTimeHistoryCache,
            threeTimeHistoryCache,
            executor,
            sixDigitsParser,
            dParser,
            oneTimeParser,
            threeTimeParser);
  }

  public void getGames() {
    repo.getGames();
  }

  public void getLottoHistory(LottoGameBaseModel model, boolean refresh) {

    switch ((model.getType() != null) ? model.getType() : "No Type") {
      case "Six Digits":
        requestData(
            sixDigitsHistoryCache,
            model,
            refresh,
            repo::getSixDigits,
            repo::getCachedSixDigitsHistory);
        break;

      case "D":
        requestData(dHistoryCache, model, refresh, repo::getD, repo::getCachedDHistory);

        break;

      case "ThreeTime":
        requestData(
            threeTimeHistoryCache,
            model,
            refresh,
            repo::getThreeTime,
            repo::getCachedThreeTimeHistory);
        break;

      case "OneTime":
        requestData(
            oneTimeHistoryCache, model, refresh, repo::getOneTime, repo::getCachedOneTimeHistory);
        break;

      default:
        view.onLastUpdatedTime("No item selected");
        view.showProgress(false);
    }
  }

  private void requestData(
      BaseCache<? extends BaseHistoryModel> table,
      LottoGameBaseModel model,
      boolean refresh,
      Consumer<LottoGameBaseModel> parser_method,
      Consumer<String> cache_method) {

    boolean flag = true;

    if (table.isCached(model.getName(), BaseCache.NAME)) {
      flag = false; // 🚩 fetch the cached data

      if (refresh) {
        table.deleteCache(model.getName(), BaseCache.NAME);
        flag = true; // 🚩 fetch the online data
      }
    }

    if (flag) parser_method.accept(model);
    else cache_method.accept(model.getName());
  }
}
