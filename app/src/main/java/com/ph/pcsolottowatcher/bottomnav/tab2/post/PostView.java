package com.ph.pcsolottowatcher.bottomnav.tab2.post;

import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheetView;
import java.util.Map;

public interface PostView extends BaseBottomSheetView {
  void postResult(int result, String message);

  void getRegex(Map<String, String> regex);
}
