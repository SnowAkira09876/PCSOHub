package com.ph.pcsolottowatcher.recyclerview.adapter.community;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.common.recyclerview.BaseFirebaseAdapter;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.databinding.CommentItemBinding;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import com.ph.pcsolottowatcher.recyclerview.holder.community.comment.CommentViewHolder;

public class CommentAdapter extends BaseFirebaseAdapter<CommentItemModel> {
  private UserDataHelper userDataHelper;

  public CommentAdapter(
      Class<CommentItemModel> modelClass, Query query, UserDataHelper userDataHelper) {
    super(modelClass, query);
    this.userDataHelper = userDataHelper;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CommentViewHolder(
        CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  protected void onBindViewHolder(ViewHolder holder, int position, CommentItemModel model) {
    ((CommentViewHolder) holder).bind(model, userDataHelper);
  }
}
