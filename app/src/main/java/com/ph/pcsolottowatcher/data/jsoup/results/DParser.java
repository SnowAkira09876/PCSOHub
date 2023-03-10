package com.ph.pcsolottowatcher.data.jsoup.results;

import android.os.Handler;
import com.ph.pcsolottowatcher.data.jsoup.BaseParser;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DParser extends BaseParser<DHistoryModel> {

  @Inject
  public DParser(Handler handler) {
    super(handler);
  }

  @Override
  protected ArrayList<DHistoryModel> getHistory() {
    list.clear();
    Element history_table = history.select("table").get(tableNumber);
    Element history_body = history_table.select("tbody").get(0);

    Elements history_rows = history_body.select("tr");
    for (Element tr : history_rows) {
      Elements item = tr.select("td");
      if (item.size() == 2) {
        DHistoryModel model = new DHistoryModel();
        model.setName(name);
        model.setDate(item.get(0).text());
        model.setNumber(item.get(1).text());

        list.add(model);
        baseCache.addHistory(model);
      }
    }
    return list;
  }
}
