package com.ph.pcsolottowatcher.common.recyclerview;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.pojos.BaseFirebaseModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;

public abstract class BaseFirebaseAdapter<T extends BaseFirebaseModel>
    extends FirebaseRecyclerAdapter<T, RecyclerView.ViewHolder> {

  private Class<T> modelClass;

  public BaseFirebaseAdapter(Class<T> modelClass, Query query) {
    super(new FirebaseRecyclerOptions.Builder<T>().setQuery(query, modelClass).build());
    this.modelClass = modelClass;
  }

  public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

  protected abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position, T model);

  @SuppressLint("NotifyDataSetChanged")
  public void updateQuery(Query newQuery) {
    FirebaseRecyclerOptions<T> options =
        new FirebaseRecyclerOptions.Builder<T>().setQuery(newQuery, modelClass).build();
    updateOptions(options);
    notifyDataSetChanged();
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
