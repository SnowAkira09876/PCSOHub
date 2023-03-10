package com.ph.pcsolottowatcher.bottomnav.tab2.comment;

import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheetView;

public interface CommentView extends BaseBottomSheetView {

  void getQuery(Query query);

  void commentResult(int result, String message);
}
