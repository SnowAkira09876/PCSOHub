package com.ph.pcsolottowatcher.bottomnav.tab2.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheet;
import com.ph.pcsolottowatcher.common.recyclerview.BaseFirebaseAdapter;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.databinding.BottomSheetCommentBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.di.FeedComponent;
import com.ph.pcsolottowatcher.pojos.firebase.CommentItemModel;
import com.ph.pcsolottowatcher.recyclerviews.AdapterFactory;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends BaseBottomSheet<CommentPresenter> implements CommentView {
  private BottomSheetCommentBinding binding;
  private String id;
  private TextInputLayout tl_comment;
  private TextInputEditText ti_comment;
  private RecyclerView rv;
  private BaseFirebaseAdapter comment_adapter;
  private LinearLayoutManager llm;
  private List<CommentItemModel> list;
  private MainActivityViewModel viewModel;
  private Query query;

  FeedDataHelper feedDataHelper;

  UserDataHelper userDataHelper;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();

    FeedComponent feedComponent = appComponent.getFeedComponent().create();
    this.feedDataHelper = feedComponent.getFeedDataHelper();
    this.userDataHelper = appComponent.getUserDataHelper();
  }

  @Override
  protected CommentPresenter createPresenter() {
    return new CommentPresenter(this, feedDataHelper);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.id = getArguments() != null ? getArguments().getString("id") : null;
    this.list = new ArrayList<>();
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    presenter.getQuery(id);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
    this.binding = BottomSheetCommentBinding.inflate(inflater, parent, false);
    this.view = binding.getRoot();
    return super.onCreateView(inflater, parent, bundle);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    comment_adapter.stopListening();
  }

  @Override
  public void getQuery(Query query) {
    this.query = query;
  }

  @Override
  public void commentResult(int result, String message) {
    switch (result) {
      case -1:
        viewModel.setDialogLogin();
        break;
      case 0:
        viewModel.setMessage(message);
        break;
    }
  }

  @Override
  protected void onsetViewBinding() {
    this.ti_comment = binding.tiComment;
    this.tl_comment = binding.tlComment;
    this.rv = binding.rv;
  }

  @Override
  protected void onsetViews() {
    this.llm = new LinearLayoutManager(getActivity());
    llm.setReverseLayout(true);
    llm.setStackFromEnd(true);
    this.comment_adapter = AdapterFactory.getCommentAdapter(query, userDataHelper);

    tl_comment.setEndIconOnClickListener(
        v -> {
          String comment = ti_comment.getText().toString();
          if (comment.length() != 0) {
            CommentItemModel model = new CommentItemModel();
            model.setId(id);
            model.setComment(comment);
            presenter.setComment(model);
            ti_comment.getText().clear();
            return;
          }
        });

    rv.setLayoutManager(llm);
    rv.setAdapter(comment_adapter);

    comment_adapter.startListening();
  }
}
