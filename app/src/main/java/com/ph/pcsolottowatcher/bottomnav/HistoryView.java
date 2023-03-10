package com.ph.pcsolottowatcher.bottomnav;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentView;
import java.util.List;

public interface HistoryView<T> extends BaseFragmentView {
  void occupyRecyclerView(List<T> list);

  void filteredHistory(List<T> list);
}
