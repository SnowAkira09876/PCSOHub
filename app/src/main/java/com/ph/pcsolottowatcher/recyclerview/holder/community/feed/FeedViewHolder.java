package com.ph.pcsolottowatcher.recyclerview.holder.community.feed;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.databinding.FeedItemBinding;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import com.ph.pcsolottowatcher.recyclerview.RootAdapter;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;
import com.squareup.picasso.Picasso;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FeedViewHolder extends RecyclerView.ViewHolder {
  private TextView tvLottoName, tvUserName, tvTime, tvLikesCount, tvCommentsCount;
  private ImageView ivHeart, ivSave;
  private ShapeableImageView ivProfile;
  private LinearLayout llComment, llLike, llCopy;
  private CircularDigitView circView;
  private FeedDataHelper feedDataHelper;
  private final ClipboardManager clipboard;

  public FeedViewHolder(FeedItemBinding binding) {
    super(binding.getRoot());
    this.tvLottoName = binding.tvGameName;
    this.tvUserName = binding.tvUsername;
    this.tvTime = binding.tvTime;
    this.tvLikesCount = binding.tvLikesCount;
    this.tvCommentsCount = binding.tvCommentsCount;
    this.circView = binding.circularItem;

    this.llLike = binding.llLike;
    this.llCopy = binding.llCopy;
    this.llComment = binding.llComment;
    this.ivProfile = binding.ivProfile;
    this.ivHeart = binding.ivHeart;

    this.clipboard =
        (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
  }

  public void bind(
      RootAdapter.FeedItemClickListener listener,
      PostItemModel model,
      FeedDataHelper feedDataHelper,
      UserDataHelper userDataHelper) {
    this.feedDataHelper = feedDataHelper;

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

    tvLottoName.setText(model.getLottoName());
    tvUserName.setText(model.getUserName());
    circView.setText(model.getNumber().split("-"));

    setTime(model);
    getLikesCount(model);
    getCommentsCount(model);
    isLiked(model);

    llLike.setOnClickListener(
        v -> {
          listener.onLikeClick(model);

          setLike(listener, model);
        });

    llComment.setOnClickListener(
        v -> {
          listener.onCommentClick(model);
        });

    llCopy.setOnClickListener(
        v -> {
          ClipData clip = ClipData.newPlainText("number", model.getNumber());
          clipboard.setPrimaryClip(clip);
          Toast.makeText(
                  itemView.getContext(),
                  model.getNumber() + " copied to clipboard",
                  Toast.LENGTH_SHORT)
              .show();
        });
  }

  private void setTime(PostItemModel model) {
    long currentTime = System.currentTimeMillis();
    long resolution = DateUtils.MINUTE_IN_MILLIS;
    CharSequence relativeTimeString =
        DateUtils.getRelativeTimeSpanString((long) model.getTime(), currentTime, resolution);

    tvTime.setText(relativeTimeString);
  }

  private void getLikesCount(PostItemModel model) {
    Consumer<Integer> consumer =
        (likes) -> {
          String likeExt = likes > 1 ? " likes" : " like";
          tvLikesCount.setText(String.valueOf(likes) + likeExt);
        };

    feedDataHelper.getLikes(model.getId(), consumer);
  }

  private void setLike(RootAdapter.FeedItemClickListener listener, PostItemModel model) {

    BiConsumer<Integer, Boolean> consumer =
        (result, like) -> {
          if (result == -1) listener.showLoginDialog();
          else if (result == 0) listener.setUserNameAlert(FeedDataHelper.PROMPT_NO_USERNAME);
          else if (result == 1 && like == true) ivHeart.setImageResource(R.drawable.ic_heart_fill);
          else if (result == 1 && like == false)
            ivHeart.setImageResource(R.drawable.ic_heart_outline);
        };

    feedDataHelper.setLike(model.getId(), consumer);
  }

  private void getCommentsCount(PostItemModel model) {
    Consumer<Integer> consumer =
        count -> {
          String commentExt = count > 1 ? " comments" : " comment";
          tvCommentsCount.setText(String.valueOf(count) + commentExt);
        };

    feedDataHelper.getCommentsCount(model.getId(), consumer);
  }

  private void isLiked(PostItemModel model) {
    Consumer<Boolean> consumer =
        (result) -> {
          if (result) ivHeart.setImageResource(R.drawable.ic_heart_fill);
          else ivHeart.setImageResource(R.drawable.ic_heart_outline);
        };

    feedDataHelper.userLikedThisPost(model.getId(), consumer);
  }
}
