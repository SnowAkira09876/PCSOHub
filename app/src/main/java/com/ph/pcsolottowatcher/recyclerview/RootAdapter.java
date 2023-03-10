package com.ph.pcsolottowatcher.recyclerview;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;

public abstract class RootAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

  public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

  public abstract void onBindViewHolder(VH parent, int position);

  public abstract int getItemCount();

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
