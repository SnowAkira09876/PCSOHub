package com.ph.pcsolottowatcher.common.recyclerview;

import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;

public abstract class BaseListAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> {

  public BaseListAdapter(DiffUtil.ItemCallback<T> diff) {
    super(diff);
  }

  public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

  public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

  public interface ItemClickListener {
    void onItemClick(LottoGameBaseModel model);
  }

  public interface FeedItemClickListener {
    void onCommentClick(PostItemModel model);

    void onLikeClick(PostItemModel model);

    void showLoginDialog();

    void setUserNameAlert(String message);

    void onDataChanged();
  }

  public interface SearchHistoryItemClickListener {
    void onSearchClick(String history);

    void onPutTextToSearch(String history);
  }
}
