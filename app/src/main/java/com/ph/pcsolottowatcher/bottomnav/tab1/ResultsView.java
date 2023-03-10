package com.ph.pcsolottowatcher.bottomnav.tab1;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentView;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import java.util.ArrayList;
import java.util.List;

public interface ResultsView extends BaseFragmentView {
  void getGames(List<LottoGameBaseModel> list);

  void getSixDigitHistory(ArrayList<SixDigitsHistoryModel> sixDigitList);

  void getDHistory(ArrayList<DHistoryModel> dList);

  void getThreeTimeHistory(ArrayList<ThreeTimeHistoryModel> threeTimeHistoryList);

  void getOneTimeHistory(ArrayList<OneTimeHistoryModel> oneTimeHistoryList);

  void showError(String title);

  void onLastUpdatedTime(String time);

  void showProgress(boolean show);

  void onBusyParsing(boolean busy);
}
