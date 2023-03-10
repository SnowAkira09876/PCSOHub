package com.ph.pcsolottowatcher.bottomnav.tab2.comment;

import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheetPresenter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import java.lang.ref.WeakReference;
import java.util.function.BiConsumer;

public class CommentPresenter extends BaseBottomSheetPresenter<CommentView> {
  private CommentRepository repo;

  public CommentPresenter(CommentView view, FeedDataHelper feedDataHelper) {
    super(view);
    this.repo = new CommentRepository(view, feedDataHelper);
  }

  public void setComment(CommentItemModel model) {
    repo.setComment(model);
  }

  public void getQuery(String id) {
    repo.getQuery(id);
  }

  public static class CommentRepository {
    private WeakReference<CommentView> view;
    private FeedDataHelper feedDataHelper;

    public CommentRepository(CommentView view, FeedDataHelper feedDataHelper) {
      this.view = new WeakReference<>(view);
      this.feedDataHelper = feedDataHelper;
    }

    public void setComment(CommentItemModel model) {
      BiConsumer<Integer, String> consumer =
          (result, message) -> {
            switch (result) {
              case -1:
                view.get().commentResult(-1, message);
                break;
              case 0:
                view.get().commentResult(0, message);
                break;
              case 1:
                view.get().commentResult(1, message);
                break;
            }
          };

      feedDataHelper.setComment(model, consumer);
    }

    public void getQuery(String id) {
      view.get().getQuery(feedDataHelper.getQueryComments(id));
    }
  }
}
