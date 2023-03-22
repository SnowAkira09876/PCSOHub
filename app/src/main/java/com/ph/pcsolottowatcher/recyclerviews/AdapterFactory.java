package com.ph.pcsolottowatcher.recyclerviews;

import androidx.recyclerview.widget.DiffUtil;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.common.recyclerview.BaseFirebaseAdapter;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.pojos.BaseHistoryModel;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.adapter.GamesAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.allresults.AllResultsAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.community.CommentAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.community.FeedAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.results.DHistoryAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.results.OneTimeHistoryAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.results.SearchHistoryAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.results.SixDigitsHistoryAdapter;
import com.ph.pcsolottowatcher.recyclerviews.adapter.results.ThreeTimeHistoryAdapter;
import java.util.Objects;

public class AdapterFactory {

  public static BaseListAdapter<SixDigitsHistoryModel> getSixDigitHistoryAdapter() {
    DiffUtil.ItemCallback<SixDigitsHistoryModel> diffUtil = getDiffUtil();

    return new SixDigitsHistoryAdapter(diffUtil);
  }

  public static BaseListAdapter<DHistoryModel> getDHistoryAdapter() {
    DiffUtil.ItemCallback<DHistoryModel> diffUtil = getDiffUtil();

    return new DHistoryAdapter(diffUtil);
  }

  public static BaseListAdapter<ThreeTimeHistoryModel> getThreeTimeHistoryAdapter() {

    DiffUtil.ItemCallback<ThreeTimeHistoryModel> diffUtil = getDiffUtil();

    return new ThreeTimeHistoryAdapter(diffUtil);
  }

  public static BaseListAdapter<OneTimeHistoryModel> getOneTimeHistoryAdapter() {

    DiffUtil.ItemCallback<OneTimeHistoryModel> diffUtil = getDiffUtil();

    return new OneTimeHistoryAdapter(diffUtil);
  }

  public static BaseListAdapter<LocalHistoryModel> getAllResultsAdapter() {

    DiffUtil.ItemCallback<LocalHistoryModel> diffUtil = getDiffUtil();

    return new AllResultsAdapter(diffUtil);
  }

  public static BaseListAdapter<LottoGameBaseModel> getGamesAdapter(
      BaseListAdapter.ItemClickListener listener) {

    DiffUtil.ItemCallback<LottoGameBaseModel> diffUtil = getGamesDiffUtil();

    return new GamesAdapter(diffUtil, listener);
  }

  public static BaseListAdapter<String> getSearchHistoryAdapter(
      BaseListAdapter.SearchHistoryItemClickListener listener) {
    DiffUtil.ItemCallback<String> diffUtil = getSearchDiffUtil();

    return new SearchHistoryAdapter(diffUtil, listener);
  }

  public static BaseFirebaseAdapter getFeedAdapter(
      BaseListAdapter.FeedItemClickListener listener,
      Query query,
      PostCache postCache,
      FeedDataHelper feedDataHelper,
      UserDataHelper userDataHelper) {

    return new FeedAdapter(
        listener, PostItemModel.class, query, postCache, feedDataHelper, userDataHelper);
  }

  public static BaseFirebaseAdapter getCommentAdapter(Query query, UserDataHelper userDataHelper) {

    return new CommentAdapter(CommentItemModel.class, query, userDataHelper);
  }

  public static <T extends BaseHistoryModel> DiffUtil.ItemCallback<T> getDiffUtil() {
    return new DiffUtil.ItemCallback<T>() {
      @Override
      public boolean areItemsTheSame(T oldItem, T newItem) {
        return oldItem.getName() == newItem.getName();
      }

      @Override
      public boolean areContentsTheSame(T oldItem, T newItem) {
        return Objects.equals(oldItem, newItem);
      }
    };
  }

  public static DiffUtil.ItemCallback<LottoGameBaseModel> getGamesDiffUtil() {
    return new DiffUtil.ItemCallback<>() {
      @Override
      public boolean areItemsTheSame(LottoGameBaseModel oldItem, LottoGameBaseModel newItem) {
        return oldItem.getName() == newItem.getName();
      }

      @Override
      public boolean areContentsTheSame(LottoGameBaseModel oldItem, LottoGameBaseModel newItem) {
        return Objects.equals(oldItem, newItem);
      }
    };
  }

  public static DiffUtil.ItemCallback<String> getSearchDiffUtil() {
    return new DiffUtil.ItemCallback<>() {
      @Override
      public boolean areItemsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
      }

      @Override
      public boolean areContentsTheSame(String oldItem, String newItem) {
        return Objects.equals(oldItem, newItem);
      }
    };
  }
}
