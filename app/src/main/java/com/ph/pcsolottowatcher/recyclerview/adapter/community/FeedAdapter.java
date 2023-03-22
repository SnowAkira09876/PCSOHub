package com.ph.pcsolottowatcher.recyclerview.adapter.community;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.common.recyclerview.BaseFirebaseAdapter;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.databinding.FeedItemBinding;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import com.ph.pcsolottowatcher.recyclerview.holder.community.feed.FeedViewHolder;

public class FeedAdapter extends BaseFirebaseAdapter<PostItemModel> {
  private BaseListAdapter.FeedItemClickListener listener;
  private PostCache postCache;
  private FeedDataHelper feedDataHelper;
  private UserDataHelper userDataHelper;
  private int newPosts = 0;

  public FeedAdapter(
      BaseListAdapter.FeedItemClickListener listener,
      Class<PostItemModel> modelClass,
      Query query,
      PostCache postCache,
      FeedDataHelper feedDataHelper,
      UserDataHelper userDataHelper) {
    super(modelClass, query);
    this.listener = listener;
    this.postCache = postCache;
    this.feedDataHelper = feedDataHelper;
    this.userDataHelper = userDataHelper;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FeedViewHolder(
        FeedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  protected void onBindViewHolder(ViewHolder holder, int position, PostItemModel model) {
    ((FeedViewHolder) holder).bind(listener, model, feedDataHelper, userDataHelper);
    if (!postCache.isCached(model.getId(), PostCache.POST)) {
      postCache.addPostId(model.getId());
      listener.onDataChanged();
    }
  }

  @Override
  public void onDataChanged() {
    super.onDataChanged();
    listener.onDataChanged();
  }
}
