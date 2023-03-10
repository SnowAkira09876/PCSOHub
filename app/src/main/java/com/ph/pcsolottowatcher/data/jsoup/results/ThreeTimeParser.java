package com.ph.pcsolottowatcher.data.jsoup.results;

import android.os.Handler;
import com.ph.pcsolottowatcher.data.jsoup.BaseParser;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ThreeTimeParser extends BaseParser<ThreeTimeHistoryModel> {
  private String timeOne, timeTwo, timeThree;

  @Inject
  public ThreeTimeParser(Handler handler) {
    super(handler);
  }

  @Override
  protected ArrayList<ThreeTimeHistoryModel> getHistory() {
    list.clear();
    Element history_table = history.select("table").get(tableNumber);

    Element history_head = history_table.select("thead").get(0);
    Elements history_rows_head = history_head.select("tr");

    for (Element thead : history_rows_head) {
      Elements itemHead = thead.select("th");
      if (itemHead.size() == 4) {
        timeOne = itemHead.get(1).text();
        timeTwo = itemHead.get(2).text();
        timeThree = itemHead.get(3).text();
      }
    }

    Element history_body = history_table.select("tbody").get(0);
    Elements history_rows = history_body.select("tr");

    for (Element tr : history_rows) {
      Elements item = tr.select("td");
      if (item.size() == 4) {
        ThreeTimeHistoryModel model = new ThreeTimeHistoryModel();

        model.setName(name);
        model.setDate(item.get(0).text());
        model.setTimeOne(timeOne);
        model.setTimeTwo(timeTwo);
        model.setTimeThree(timeThree);

        model.setTimeOneResult(item.get(1).text());
        model.setTimeTwoResult(item.get(2).text());
        model.setTimeThreeResult(item.get(3).text());

        list.add(model);
        baseCache.addHistory(model);
      }
    }
    return list;
  }
}
