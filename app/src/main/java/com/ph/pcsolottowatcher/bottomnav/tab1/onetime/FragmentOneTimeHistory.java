package com.ph.pcsolottowatcher.fragments.tab1.onetime;

import android.os.Build;
import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.AdapterFactory;

public class FragmentOneTimeHistory extends HistoryFragment<OneTimeHistoryModel> {

  @Override
  protected BaseListAdapter<OneTimeHistoryModel> getAdapter() {
    return AdapterFactory.getOneTimeHistoryAdapter();
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onsetBundle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      this.list =
          getArguments().getParcelableArrayList("OneTimeHistoryList", OneTimeHistoryModel.class);
    } else {
      this.list = getArguments().getParcelableArrayList("DHistoryList");
    }
    presenter.setHistoryList(list);
  }
}
