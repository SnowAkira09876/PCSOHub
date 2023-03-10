package com.ph.pcsolottowatcher.fragments.tab1.d;

import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;
import java.util.List;

public class FragmentDHistory extends HistoryFragment<DHistoryModel> {

  @Override
  protected RootListAdapter<DHistoryModel> getAdapter() {
    return AdapterFactory.getDHistoryAdapter();
  }

  @Override
  protected void onsetBundle() {
    List<DHistoryModel> allHistory = getArguments().getParcelableArrayList("DHistoryList");
    presenter.setHistoryList(allHistory);
  }
}
