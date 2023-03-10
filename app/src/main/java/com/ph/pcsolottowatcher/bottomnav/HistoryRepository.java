package com.ph.pcsolottowatcher.bottomnav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HistoryRepository<H extends BaseHistoryModel> {
  private List<H> beforeList;
  private String sText;
  private WeakReference<HistoryView<H>> view;

  public HistoryRepository(HistoryView<H> view) {
    this.view = new WeakReference<>(view);
  }

  public HistoryRepository setSText(@Nullable String sText) {
    this.sText = sText;
    return this;
  }

  public void setHistory(@NonNull List<H> beforeList) {
    this.beforeList = beforeList;
  }

  public void getHistory() {
    if (sText != null) {
      String regex = sText + "|" + sText.replace(" ", "[- ]") + ".*";

      List<H> afterList =
          beforeList.stream()
              .filter(
                  model -> {
                    String date = "";
                    String number = "";
                    String modelToSearch = "";
                    if (model instanceof SixDigitsHistoryModel) {
                      number = ((SixDigitsHistoryModel) model).getNumber();
                      date = ((SixDigitsHistoryModel) model).getDate();
                    } else if (model instanceof DHistoryModel) {
                      number = ((DHistoryModel) model).getNumber();
                      date = ((DHistoryModel) model).getDate();
                    } else if (model instanceof ThreeTimeHistoryModel) {
                      date = ((ThreeTimeHistoryModel) model).getDate();
                      number =
                          ((ThreeTimeHistoryModel) model).getTimeOneResult()
                              + ((ThreeTimeHistoryModel) model).getTimeTwoResult()
                              + ((ThreeTimeHistoryModel) model).getTimeThreeResult();
                    } else if (model instanceof OneTimeHistoryModel) {
                      number = ((OneTimeHistoryModel) model).getTimeOneResult();
                      date = ((OneTimeHistoryModel) model).getDate();
                    } else if (model instanceof LocalHistoryModel) {
                      number = ((LocalHistoryModel) model).getDate() + " ";
                      date = ((LocalHistoryModel) model).getNumber();
                    }
                    modelToSearch = date + number;
                    Matcher matcher = Pattern.compile(regex).matcher(modelToSearch);
                    return matcher.find();
                  })
              .collect(Collectors.toList());

      view.get().filteredHistory(afterList);
    } else {
      view.get().occupyRecyclerView(beforeList);
    }
  }
}
