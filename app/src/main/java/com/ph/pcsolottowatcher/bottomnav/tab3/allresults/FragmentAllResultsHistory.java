package com.ph.pcsolottowatcher.bottomnav.tab3.allresults;

import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;

public class FragmentAllResultsHistory extends HistoryFragment<LocalHistoryModel> {

  @Override
  protected void onsetBundle() {
    this.list = getArguments().getParcelableArrayList("AllResultsHistoryList");
    presenter.setHistoryList(list);
  }

  @Override
  protected RootListAdapter<LocalHistoryModel> getAdapter() {
    return AdapterFactory.getAllResultsAdapter();
  }
}
