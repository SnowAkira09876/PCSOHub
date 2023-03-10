package com.ph.pcsolottowatcher.fragments.tab1.onetime;

import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;

public class FragmentOneTimeHistory extends HistoryFragment<OneTimeHistoryModel> {

  @Override
  protected RootListAdapter<OneTimeHistoryModel> getAdapter() {
    return AdapterFactory.getOneTimeHistoryAdapter();
  }

  @Override
  protected void onsetBundle() {
    this.list = getArguments().getParcelableArrayList("OneTimeHistoryList");
    presenter.setHistoryList(list);
  }
}
