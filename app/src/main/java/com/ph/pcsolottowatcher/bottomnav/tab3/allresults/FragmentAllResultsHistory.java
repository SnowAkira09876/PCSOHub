package com.ph.pcsolottowatcher.bottomnav.tab3.allresults;

import android.os.Build;
import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;

public class FragmentAllResultsHistory extends HistoryFragment<LocalHistoryModel> {

  @Override
  protected BaseListAdapter<LocalHistoryModel> getAdapter() {
    return AdapterFactory.getAllResultsAdapter();
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onsetBundle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      this.list =
          getArguments().getParcelableArrayList("AllResultsHistoryList", LocalHistoryModel.class);
    } else {
      this.list = getArguments().getParcelableArrayList("AllResultsHistoryList");
    }
    presenter.setHistoryList(list);
  }
}
