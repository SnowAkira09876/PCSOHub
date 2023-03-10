package com.ph.pcsolottowatcher.preference;

import android.net.Uri;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph.pcsolottowatcher.common.preference.BasePreferencePresenter;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import java.lang.ref.WeakReference;
import java.util.function.BiConsumer;

public class MainSettingsFragmentPresenter
    extends BasePreferencePresenter<MainSettingsFragmentView> {
  private MainSettingsFragmentRepository repo;

  public MainSettingsFragmentPresenter(
      MainSettingsFragmentView view, PrefHelper prefHelper, UserDataHelper userDataHelper) {
    super(view);
    this.repo = new MainSettingsFragmentRepository(view, prefHelper, userDataHelper);
  }

  public void setDynamicTheme(String key, boolean val) {
    repo.setDynamicTheme(key, val);
  }

  public void getInfo() {
    repo.getInfo();
  }

  public void uploadProfile(Uri uri, String fileName) {
    repo.uploadProfile(uri, fileName);
  }

  public void onPause() {
    repo.onPause();
  }

  public void sendEmailVerification() {
    repo.sendEmailVerification();
  }

  public static class MainSettingsFragmentRepository {
    private WeakReference<MainSettingsFragmentView> view;
    private PrefHelper prefHelper;
    private UserDataHelper userDataHelper;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener listener;

    public MainSettingsFragmentRepository(
        MainSettingsFragmentView view, PrefHelper prefHelper, UserDataHelper userDataHelper) {
      this.view = new WeakReference<>(view);
      this.prefHelper = prefHelper;
      this.userDataHelper = userDataHelper;
    }

    public void setDynamicTheme(String key, boolean val) {
      prefHelper.setBoolean(key, val);
    }

    public void getInfo() {
      this.listener =
          newUser -> {
            if (newUser.getCurrentUser() != null) {
              view.get()
                  .getInfo(
                      newUser.getCurrentUser().getDisplayName(),
                      newUser.getCurrentUser().getEmail());
            } else view.get().getInfo("Account", "Tap to login or register an account");
          };

      mAuth.addAuthStateListener(listener);
    }

    public void uploadProfile(Uri uri, String fileName) {
      FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
      if (mUser != null && uri != null) {
        BiConsumer<Integer, Boolean> consumer =
            (progress, result) -> {
              view.get().onUploading(progress, result);
            };

        userDataHelper.uploadProfile(mUser.getUid(), uri, fileName, consumer);
      } else view.get().setMessage("You are currently not logged in");
    }

    public void sendEmailVerification() {
      FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
      if (!mUser.isEmailVerified()) {
        mUser
            .sendEmailVerification()
            .addOnCompleteListener(
                task -> {
                  if (task.isSuccessful()) {
                    view.get().onEmailVerification(false, mUser.getEmail());
                  }
                });
      }
    }

    public void onPause() {
      mAuth.removeAuthStateListener(listener);
    }
  }
}
