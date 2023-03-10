package com.ph.pcsolottowatcher.fragments.tab1.threetime;

import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;

public class FragmentThreeTimeHistory extends HistoryFragment<ThreeTimeHistoryModel> {

  @Override
  protected RootListAdapter<ThreeTimeHistoryModel> getAdapter() {
    return AdapterFactory.getThreeTimeHistoryAdapter();
  }

  @Override
  protected void onsetBundle() {
    this.list = getArguments().getParcelableArrayList("ThreeTimeHistoryList");
    presenter.setHistoryList(list);
  }
}
