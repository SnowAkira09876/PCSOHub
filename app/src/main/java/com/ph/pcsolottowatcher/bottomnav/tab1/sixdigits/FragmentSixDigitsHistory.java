package com.ph.pcsolottowatcher.fragments.tab1.sixdigits;

import android.os.Build;
import com.ph.pcsolottowatcher.bottomnav.HistoryFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.AdapterFactory;

public class FragmentSixDigitsHistory extends HistoryFragment<SixDigitsHistoryModel> {

  @Override
  protected BaseListAdapter<SixDigitsHistoryModel> getAdapter() {
    return AdapterFactory.getSixDigitHistoryAdapter();
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onsetBundle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      this.list =
          getArguments()
              .getParcelableArrayList("SixDigitsHistoryList", SixDigitsHistoryModel.class);
    } else {
      this.list = getArguments().getParcelableArrayList("SixDigitsHistoryList");
    }
    presenter.setHistoryList(list);
  }
}
