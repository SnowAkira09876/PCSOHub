package com.ph.pcsolottowatcher.bottomnav.tab2.feed;

import com.ph.pcsolottowatcher.common.fragment.BaseFragmentPresenter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;

public class FeedPresenter extends BaseFragmentPresenter<FeedView> {
  private Repository repo;

  public FeedPresenter(
      FeedView view, JsonHelper jsonHelper, PostCache postCache, FeedDataHelper feedDataHelper) {
    super(view);
    this.repo = new Repository(view, jsonHelper, postCache, feedDataHelper);
  }

  public void getGames() {
    repo.getGames();
  }

  public void getQuery() {
    repo.getQuery();
  }

  public void getFeedItems() {
    repo.getFeedItems();
  }

  private static class Repository {
    private WeakReference<FeedView> view;
    private JsonHelper jsonHelper;
    private FeedDataHelper feedDataHelper;
    private PostCache postCache;

    public Repository(
        FeedView view, JsonHelper jsonHelper, PostCache postCache, FeedDataHelper feedDataHelper) {
      this.view = new WeakReference<>(view);
      this.jsonHelper = jsonHelper;
      this.feedDataHelper = feedDataHelper;
      this.postCache = postCache;
    }

    public void getGames() {
      Consumer<List<LottoGameBaseModel>> consumer =
          list -> {
            view.get().getGames(list);
          };
      jsonHelper.getLottoGamesList(consumer, JsonHelper.LOTTO_ARRAY_NAME);
    }

    public void getQuery() {
      view.get().getQuery(feedDataHelper.getQueryNumbers());
    }

    public void getFeedItems() {
      Consumer<Integer> consumer =
          allPosts -> {
            int unreadPosts = allPosts - postCache.getSeenPostsSize();
            if (unreadPosts < 0) postCache.resetCache();
            else view.get().showBadge(unreadPosts);
          };
      feedDataHelper.getPostedNumbers(consumer);
    }
  }
}
