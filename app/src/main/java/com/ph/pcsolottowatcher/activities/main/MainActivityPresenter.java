package com.ph.pcsolottowatcher.activities.main;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph.pcsolottowatcher.common.activity.BaseActivityPresenter;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.data.sql.search.SearchHistoryCache;
import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;

public class MainActivityPresenter extends BaseActivityPresenter<MainActivityView> {
  private MainActivityView view;
  private BaseActivityPresenter.Repo baseRepo;
  private SearchHistoryCache searchHistoryCache;
  private Repo repo;

  public MainActivityPresenter(
      MainActivityView view,
      PrefHelper prefHelper,
      SearchHistoryCache searchHistoryCache,
      UserDataHelper userDataHelper) {
    super(view);
    this.baseRepo = new BaseActivityPresenter.Repo<MainActivityView>(view, prefHelper);
    this.repo = new Repo(view, searchHistoryCache, userDataHelper);
  }

  @Override
  public void applyDynamicTheme() {
    baseRepo.applyDynamicTheme();
  }

  public void getSearchHistory() {
    repo.getSearchHistory();
  }

  public void addSearchHistory(String history) {
    repo.addSearchHistory(history);
  }

  public void getProfile() {
    repo.getProfile();
  }

  public void checkEmailVerified() {
    repo.checkEmailVerified();
  }

  private static class Repo {
    private SearchHistoryCache searchHistoryCache;
    private WeakReference<MainActivityView> view;
    private UserDataHelper userDataHelper;
    private FirebaseAuth.AuthStateListener listener;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public Repo(
        MainActivityView view,
        SearchHistoryCache searchHistoryCache,
        UserDataHelper userDataHelper) {
      this.view = new WeakReference<>(view);
      this.searchHistoryCache = searchHistoryCache;
      this.userDataHelper = userDataHelper;
    }

    public void getSearchHistory() {
      view.get().getSearchHistory(searchHistoryCache.getSearchHistory());
    }

    public void addSearchHistory(String history) {
      if (history != null
          && history.length() > 1
          && !searchHistoryCache.isCached(history, SearchHistoryCache.SEARCH_COLUMN)) {

        searchHistoryCache.addSearch(history);
      }
    }

    public void getProfile() {
      FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
      view.get().getProfile(null);

      if (mUser != null) {
        CompletableFuture<String> profileFuture = userDataHelper.getProfile(mUser.getUid());

        profileFuture.thenAccept(
            profile -> {
              view.get().getProfile(profile);
            });

        userDataHelper.setUserName(mUser.getUid(), mUser.getDisplayName());
      }
    }

    public void checkEmailVerified() {
      FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
      if (mUser != null) {
        if (mUser.isEmailVerified()) view.get().onEmailVerification(true, mUser.getEmail());
        else view.get().onEmailVerification(false, mUser.getEmail());
      }
    }
  }
}
