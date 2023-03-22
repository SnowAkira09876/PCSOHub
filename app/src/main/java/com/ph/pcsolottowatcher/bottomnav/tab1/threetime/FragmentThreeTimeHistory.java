package com.ph.pcsolottowatcher.fragments.tab1.threetime;

import android.os.Build;
import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;

public class FragmentThreeTimeHistory extends HistoryFragment<ThreeTimeHistoryModel> {

  @Override
  protected BaseListAdapter<ThreeTimeHistoryModel> getAdapter() {
    return AdapterFactory.getThreeTimeHistoryAdapter();
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onsetBundle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      this.list =
          getArguments()
              .getParcelableArrayList("ThreeTimeHistoryList", ThreeTimeHistoryModel.class);
    } else {
      this.list = getArguments().getParcelableArrayList("ThreeTimeHistoryList");
    }
    presenter.setHistoryList(list);
  }
}
