package com.ph.pcsolottowatcher.bottomnav.tab3;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentView;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import java.util.ArrayList;
import java.util.List;

public interface AllResultsView extends BaseFragmentView {
  void getGames(List<LottoGameBaseModel> list);

  void getFilteredList(ArrayList<LocalHistoryModel> filtered_list);
}
