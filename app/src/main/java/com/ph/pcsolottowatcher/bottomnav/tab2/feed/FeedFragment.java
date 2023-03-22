package com.ph.pcsolottowatcher.bottomnav.tab2.feed;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.bottomnav.tab2.comment.CommentFragment;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;
import com.ph.pcsolottowatcher.common.recyclerview.BaseFirebaseAdapter;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.databinding.FragmentHistoryBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.di.FeedComponent;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import com.ph.pcsolottowatcher.recyclerviews.AdapterFactory;
import com.ph.pcsolottowatcher.viewmodels.FeedViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import java.util.List;

public class FeedFragment extends BaseFragment<FeedPresenter>
    implements FeedView, BaseListAdapter.FeedItemClickListener {
  private FragmentHistoryBinding binding;
  private RecyclerView rv_feed;
  private LinearLayoutManager llm;
  private BaseFirebaseAdapter feedAdapter;
  private Query query;
  private MainActivityViewModel viewModel;
  private FeedViewModel community_viewModel;

  JsonHelper jsonHelper;
  PostCache postCache;
  UserDataHelper userDataHelper;
  FeedDataHelper feedDataHelper;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();

    FeedComponent feedComponent = appComponent.getFeedComponent().create();
    this.jsonHelper = appComponent.getJsonHelper();
    this.postCache = appComponent.getPostCache();
    this.feedDataHelper = feedComponent.getFeedDataHelper();
    this.userDataHelper = appComponent.getUserDataHelper();
  }

  @Override
  protected FeedPresenter createPresenter() {
    return new FeedPresenter(this, jsonHelper, postCache, feedDataHelper);
  }

  @Override
  protected void onsetViewBinding() {
    this.rv_feed = binding.rv;
  }

  @Override
  protected void onsetViews() {
    this.feedAdapter =
        AdapterFactory.getFeedAdapter(this, query, postCache, feedDataHelper, userDataHelper);
    rv_feed.setLayoutManager(llm);
    rv_feed.setAdapter(feedAdapter);
    rv_feed.addOnScrollListener(
        new RecyclerView.OnScrollListener() {
          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) {
              // SLIDEDOWN
              viewModel.setFab(false);
            }
            if (dy < 0) {
              // SLIDEUP
              viewModel.setFab(true);
            }
          }
        });

    community_viewModel
        .getModel()
        .observe(
            getViewLifecycleOwner(),
            model -> {
              feedAdapter.updateQuery(query.orderByChild("lottoName").equalTo(model.getName()));
            });

    community_viewModel
        .getSearch()
        .observe(
            getViewLifecycleOwner(),
            search -> {
              if (TextUtils.isEmpty(search)) feedAdapter.updateQuery(query);
              else
                feedAdapter.updateQuery(
                    query.orderByChild("userName").startAt(search).endAt(search + "\uf8ff"));
            });

    feedAdapter.startListening();
    presenter.getGames();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    this.community_viewModel = new ViewModelProvider(getActivity()).get(FeedViewModel.class);
    this.llm = new LinearLayoutManager(getActivity());
    llm.setReverseLayout(true);
    llm.setStackFromEnd(true);
    presenter.getQuery();
    community_viewModel.setPlaceHolder(true);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.binding = FragmentHistoryBinding.inflate(inflater, container, false);
    this.view = binding.getRoot();
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    feedAdapter.stopListening();
  }

  @Override
  public void onCommentClick(PostItemModel model) {
    Bundle bundle = new Bundle();
    bundle.putString("id", model.getId());
    CommentFragment commentFragment = new CommentFragment();
    commentFragment.setArguments(bundle);
    commentFragment.show(getParentFragmentManager(), null);
  }

  @Override
  public void onLikeClick(PostItemModel model) {}

  @Override
  public void showLoginDialog() {
    viewModel.setDialogLogin();
  }

  @Override
  public void setUserNameAlert(String message) {
    viewModel.setMessage(message);
  }

  @Override
  public void onDataChanged() {
    presenter.getFeedItems();
    community_viewModel.setPlaceHolder(false);
  }

  @Override
  public void getQuery(Query query) {
    this.query = query;
  }

  @Override
  public void showBadge(int count) {
    viewModel.setBadge(count);
  }

  @Override
  public void getGames(List<LottoGameBaseModel> list) {
    viewModel.setGames(list);
  }
}
