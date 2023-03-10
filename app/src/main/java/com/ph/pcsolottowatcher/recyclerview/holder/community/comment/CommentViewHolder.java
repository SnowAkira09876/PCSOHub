package com.ph.pcsolottowatcher.recyclerview.holder.community.comment;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.databinding.CommentItemBinding;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import com.squareup.picasso.Picasso;
import java.util.concurrent.CompletableFuture;

public class CommentViewHolder extends RecyclerView.ViewHolder {
  private ImageView ivProfile;
  private TextView tvUserName, tvComment;

  public CommentViewHolder(CommentItemBinding binding) {
    super(binding.getRoot());
    this.ivProfile = binding.ivProfile;
    this.tvUserName = binding.tvUsername;
    this.tvComment = binding.tvComment;
  }

  public void bind(CommentItemModel model, UserDataHelper userDataHelper) {
    CompletableFuture<String> future = userDataHelper.getProfile(model.getUserId());

    future.thenAccept(
        profile -> {
          Picasso.get()
              .load(profile)
              .placeholder(R.mipmap.ic_launcher)
              .fit()
              .centerCrop()
              .into(ivProfile);
        });

    tvUserName.setText(model.getUserName());
    tvComment.setText(model.getComment());
  }
}
