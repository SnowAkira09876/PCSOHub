package com.ph.pcsolottowatcher.dialogs.clearcache;

import com.ph.pcsolottowatcher.common.dialog.BaseDialogPresenter;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import java.lang.ref.WeakReference;

public class DialogFragmentClearCachePresenter
    extends BaseDialogPresenter<DialogFragmentClearCacheView> {
  private CacheRepo repo;

  public DialogFragmentClearCachePresenter(DialogFragmentClearCacheView view, PostCache postCache) {
    super(view);
    this.repo = new CacheRepo(view, postCache);
  }

  public void clearCache() {
    repo.clearCache();
  }

  private static class CacheRepo {
    private PostCache postCache;
    private WeakReference<DialogFragmentClearCacheView> view;

    public CacheRepo(DialogFragmentClearCacheView view, PostCache postCache) {
      this.postCache = postCache;
      this.view = new WeakReference<>(view);
    }

    public void clearCache() {
      postCache.resetAllCaches(postCache.getWritableDatabase());
      view.get().onClearCache();
    }
  }
}
