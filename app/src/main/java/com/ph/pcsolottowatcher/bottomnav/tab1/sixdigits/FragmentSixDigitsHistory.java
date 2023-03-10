package com.ph.pcsolottowatcher.fragments.tab1.sixdigits;

import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;

public class FragmentSixDigitsHistory extends HistoryFragment<SixDigitsHistoryModel> {

  @Override
  protected RootListAdapter<SixDigitsHistoryModel> getAdapter() {
    return AdapterFactory.getSixDigitHistoryAdapter();
  }

  @Override
  protected void onsetBundle() {
    this.list = getArguments().getParcelableArrayList("SixDigitsHistoryList");
    presenter.setHistoryList(list);
  }
}
