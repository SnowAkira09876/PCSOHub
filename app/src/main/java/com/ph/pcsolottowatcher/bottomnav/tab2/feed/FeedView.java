package com.ph.pcsolottowatcher.bottomnav.tab2.feed;

import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.common.fragment.BaseFragmentView;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import java.util.List;

public interface FeedView extends BaseFragmentView {
  void getQuery(Query query);

  void showBadge(int count);

  void getGames(List<LottoGameBaseModel> list);
}
