package com.ph.pcsolottowatcher.bottomnav;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentPresenter;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import java.util.List;

public class HistoryPresenter<T extends BaseHistoryModel>
    extends BaseFragmentPresenter<HistoryView> {
  private HistoryRepository<T> repo;

  public HistoryPresenter(HistoryView<T> view) {
    super(view);

    this.repo = new HistoryRepository<>(view);
  }

  public void getFilteredList(String sText) {
    repo.setSText(sText).getHistory();
  }

  public void setHistoryList(List<T> list) {
    repo.setHistory(list);
  }

  public void getHistoryList() {
    repo.getHistory();
  }
}
