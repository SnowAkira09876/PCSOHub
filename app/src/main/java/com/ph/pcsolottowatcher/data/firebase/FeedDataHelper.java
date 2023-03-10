package com.ph.pcsolottowatcher.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.ph.pcsolottowatcher.di.scopes.PerActivity;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.inject.Inject;

@PerActivity
public class FeedDataHelper {
  public static final String POSTS_CHILD = "posts",
      COMMENTS_CHILD = "comments",
      LIKES_CHILD = "likes",
      PROMPT_NO_USERNAME = "Please set a username in settings",
      PROMPT_NOT_LOGGEDIN = "You are currently not logged in";

  private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
  private final List<PostItemModel> post_list = new ArrayList<>();
  private final List<CommentItemModel> comment_list = new ArrayList<>();
  private final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
  private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

  @Inject
  public FeedDataHelper() {}

  public Query getQueryNumbers() {
    return mDatabase.child(POSTS_CHILD);
  }

  public Query getQueryComments(String id) {
    return mDatabase.child(COMMENTS_CHILD).child(id);
  }

  public void getPostedNumbers(Consumer<Integer> consumer) {
    ValueEventListener postListener =
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            consumer.accept((int) dataSnapshot.getChildrenCount());
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {}
        };

    mDatabase.child(POSTS_CHILD).addListenerForSingleValueEvent(postListener);
  }

  public void setNumber(PostItemModel model, BiConsumer<Integer, String> consumer) {
    String pushId = mDatabase.push().getKey();
    if (mUser != null) {
      model.setTime(ServerValue.TIMESTAMP);
      model.setUserName(mUser.getDisplayName());
      model.setUserId(mUser.getUid());
      model.setId(pushId);
      mDatabase
          .child(POSTS_CHILD)
          .child(pushId)
          .setValue(model)
          .addOnSuccessListener(
              aVoid -> {
                consumer.accept(1, "Posted succesfully");
              });
    } else consumer.accept(-1, PROMPT_NOT_LOGGEDIN);
  }

  public void setComment(CommentItemModel model, BiConsumer<Integer, String> consumer) {
    if (mUser != null) {
      model.setUserName(mUser.getDisplayName());
      model.setUserId(mUser.getUid());
      mDatabase
          .child(COMMENTS_CHILD)
          .child(model.getId())
          .push()
          .setValue(model)
          .addOnSuccessListener(
              aVoid -> {
                consumer.accept(1, "Commented succesfully");
              });
    } else consumer.accept(-1, PROMPT_NOT_LOGGEDIN);
  }

  public void setLike(String id, BiConsumer<Integer, Boolean> consumer) {
    if (mUser != null) {
      ValueEventListener likeListener =
          new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.hasChild(mUser.getDisplayName())) {
                boolean currentValue =
                    dataSnapshot.child(mUser.getDisplayName()).getValue(Boolean.class);
                mDatabase
                    .child(LIKES_CHILD)
                    .child(id)
                    .child(mUser.getDisplayName())
                    .setValue(!currentValue);
                consumer.accept(1, !currentValue);
              } else {
                mDatabase.child(LIKES_CHILD).child(id).child(mUser.getDisplayName()).setValue(true);
                consumer.accept(1, true);
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
          };

      mDatabase.child(LIKES_CHILD).child(id).addListenerForSingleValueEvent(likeListener);
    } else consumer.accept(-1, false);
  }

  public void getLikes(String id, Consumer<Integer> consumer) {
    ValueEventListener postListener =
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            int likes = 0;
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              likes++;
            }
            consumer.accept(likes);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {}
        };

    mDatabase
        .child(LIKES_CHILD)
        .child(id)
        .orderByValue()
        .equalTo(true)
        .addValueEventListener(postListener);
  }

  public void getCommentsCount(String id, Consumer<Integer> consumer) {
    ValueEventListener postListener =
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            consumer.accept((int) dataSnapshot.getChildrenCount());
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {}
        };

    mDatabase.child(COMMENTS_CHILD).child(id).addValueEventListener(postListener);
  }

  public void userLikedThisPost(String id, Consumer<Boolean> consumer) {
    if (mUser != null) {
      mDatabase
          .child(LIKES_CHILD)
          .child(id)
          .orderByValue()
          .equalTo(true)
          .addValueEventListener(
              new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  if (dataSnapshot.hasChild(mUser.getDisplayName())) consumer.accept(true);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
              });
    } else consumer.accept(false);
  }
}
