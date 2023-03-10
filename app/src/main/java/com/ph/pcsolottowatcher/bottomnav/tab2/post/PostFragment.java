package com.ph.pcsolottowatcher.bottomnav.tab2.post;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.bottomsheet.BaseBottomSheet;
import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.databinding.BottomSheetPostBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.di.FeedComponent;
import com.ph.pcsolottowatcher.pojos.firebase.PostItemModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostFragment extends BaseBottomSheet<PostPresenter> implements PostView {
  private BottomSheetPostBinding binding;
  private Map<String, String> map;
  private TextInputEditText ti_number;
  private TextInputLayout tl_post;
  private MainActivityViewModel viewModel;

  FeedDataHelper feedDataHelper;
  JsonHelper jsonHelper;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();

    FeedComponent feedComponent = appComponent.getFeedComponent().create();
    feedDataHelper = feedComponent.getFeedDataHelper();
    this.jsonHelper = appComponent.getJsonHelper();
  }

  @Override
  protected PostPresenter createPresenter() {
    return new PostPresenter(this, jsonHelper, feedDataHelper);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.map = new HashMap<>();
    this.viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    presenter.getRegex();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
    this.binding = BottomSheetPostBinding.inflate(inflater, container, false);
    this.view = binding.getRoot();

    return super.onCreateView(inflater, container, bundle);
  }

  @Override
  public void getRegex(Map<String, String> regex) {
    this.map.putAll(regex);
  }

  @Override
  public void postResult(int result, String message) {
    dismiss();
    switch (result) {
      case -1:
        viewModel.setDialogLogin();
        break;
      case 0:
        viewModel.setMessage(message);
        break;
      case 1:
        viewModel.setMessage("Successfully posted");
        break;
    }
  }

  @Override
  protected void onsetViewBinding() {
    this.ti_number = binding.tiNumber;
    this.tl_post = binding.tlPost;
  }

  @Override
  protected void onsetViews() {
    ti_number.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            PostItemModel model = new PostItemModel();
            String num = s.toString().trim(), reg = "";
            boolean matches = false;

            String[] result_num = {""};
            String[] result_lotto_name = {""};

            for (String regex : map.keySet()) {
              if (num.matches(regex)) {
                matches = true;
                reg = regex;
                break;
              }
            }

            if (matches) {
              Pattern pattern = Pattern.compile(reg);
              Matcher matcher = pattern.matcher(num);

              if (matcher.find()) {
                tl_post.setHint(map.get(reg));
                tl_post.setErrorEnabled(false);
                result_num[0] = matcher.group(1);
                result_lotto_name[0] = map.get(reg);
              }

              tl_post.setEndIconOnClickListener(
                  v -> {
                    model.setLottoName(result_lotto_name[0]);
                    model.setNumber(result_num[0]);
                    presenter.postNumber(model);
                  });
              return;
            }

            tl_post.setHint("Invalid lotto format");
            tl_post.setErrorEnabled(true);
            tl_post.setError("Be sure to separate each number by dash");
            tl_post.setEndIconOnClickListener(null);
          }
        });
  }
}
