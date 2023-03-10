package com.ph.pcsolottowatcher.data.firebase;

import android.net.Uri;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDataHelper {
  private static final String CHILD_PROFILE = "profile";
  private static final String CHILD_USERNAME = "username";
  private static final String CHILD_USERS = "users";
  private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

  @Inject
  public UserDataHelper() {}

  public void setUserName(String uid, String displayName) {
    mDatabase.child(CHILD_USERS).child(uid).child(CHILD_USERNAME).setValue(displayName);
  }

  public CompletableFuture<String> getProfile(String uid) {
    CompletableFuture<String> future = new CompletableFuture<>();

    mDatabase
        .child(CHILD_USERS)
        .child(uid)
        .addValueEventListener(
            new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(CHILD_PROFILE))
                  future.complete(dataSnapshot.child(CHILD_PROFILE).getValue().toString());
                else future.complete(null);
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {}
            });
    return future;
  }

  public void uploadProfile(
      String uid, Uri mImageUri, String fileName, BiConsumer<Integer, Boolean> consumer) {
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(CHILD_PROFILE);
    StorageReference fileReference = mStorageRef.child(uid).child(fileName);

    StorageTask mUploadTask =
        fileReference
            .putFile(mImageUri)
            .addOnProgressListener(
                taskSnapshot -> {
                  double progress =
                      (100.0 * taskSnapshot.getBytesTransferred())
                          / taskSnapshot.getTotalByteCount();

                  consumer.accept((int) progress, true);
                })
            .addOnSuccessListener(
                taskSnapshot -> {
                  Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                  result.addOnSuccessListener(
                      (Uri uri) -> {
                        mDatabase
                            .child(CHILD_USERS)
                            .child(uid)
                            .child(CHILD_PROFILE)
                            .setValue(uri.toString());

                        consumer.accept(100, false);
                      });
                });
  }
}
