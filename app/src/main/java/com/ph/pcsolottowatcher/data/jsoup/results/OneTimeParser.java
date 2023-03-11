package com.ph.pcsolottowatcher.data.jsoup.results;

import android.os.Handler;
import android.webkit.WebView;
import com.ph.pcsolottowatcher.data.jsoup.BaseParser;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OneTimeParser extends BaseParser<OneTimeHistoryModel> {
  private String timeOne;

  @Inject
  public OneTimeParser(Handler handler, WebView webView) {
    super(handler, webView);
  }

  @Override
  protected ArrayList<OneTimeHistoryModel> getHistory() {
    list.clear();
    Element history_table = history.select("table").get(tableNumber);
    Element history_head = history_table.select("thead").get(0);
    Elements history_rows_head = history_head.select("tr");

    for (Element thead : history_rows_head) {
      Elements itemHead = thead.select("th");
      if (itemHead.size() == 2) {
        timeOne = itemHead.get(1).text();
      }
    }

    Element history_body = history_table.select("tbody").get(0);
    Elements history_rows = history_body.select("tr");

    for (Element tr : history_rows) {
      Elements item = tr.select("td");
      if (item.size() == 2) {
        OneTimeHistoryModel model = new OneTimeHistoryModel();

        model.setName(name);
        model.setDate(item.get(0).text());
        model.setTimeOne(timeOne);
        model.setTimeOneResult(item.get(1).text());

        list.add(model);
        baseCache.addHistory(model);
      }
    }
    return list;
  }
}
