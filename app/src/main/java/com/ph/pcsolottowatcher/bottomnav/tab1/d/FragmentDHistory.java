package com.ph.pcsolottowatcher.fragments.tab1.d;

import android.os.Build;
import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;

public class FragmentDHistory extends HistoryFragment<DHistoryModel> {

  @Override
  protected BaseListAdapter<DHistoryModel> getAdapter() {
    return AdapterFactory.getDHistoryAdapter();
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onsetBundle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      this.list = getArguments().getParcelableArrayList("DHistoryList", DHistoryModel.class);
    } else {
      this.list = getArguments().getParcelableArrayList("DHistoryList");
    }
    presenter.setHistoryList(list);
  }
}
