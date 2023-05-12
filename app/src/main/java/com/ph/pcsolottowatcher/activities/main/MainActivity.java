package com.ph.pcsolottowatcher.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.snackbar.Snackbar;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.activities.preference.MainPreferenceActivity;
import com.ph.pcsolottowatcher.bottomnav.tab2.post.PostFragment;
import com.ph.pcsolottowatcher.common.activity.BaseActivity;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.data.sql.search.SearchHistoryCache;
import com.ph.pcsolottowatcher.databinding.ActivityMainBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.dialogs.loginprompt.DialogFragmentLoginPrompt;
import com.ph.pcsolottowatcher.dialogs.sort.DialogFragmentSort;
import com.ph.pcsolottowatcher.dialogs.verifyemail.FragmentDialogVerifyEmail;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.recyclerviews.AdapterFactory;
import com.ph.pcsolottowatcher.recyclerviews.adapter.GamesAdapter;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;
import com.ph.pcsolottowatcher.viewmodels.FeedViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainActivityViewModel;
import com.ph.pcsolottowatcher.viewmodels.ResultsViewModel;
import com.ph.pcsolottowatcher.widgets.avataricon.AvatarIconMenu;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

public class MainActivity extends BaseActivity<MainActivityPresenter>
    implements MainActivityView,
        BaseListAdapter.SearchHistoryItemClickListener,
        BaseListAdapter.ItemClickListener {

  private ActivityMainBinding binding;
  private BottomNavigationView bottomNavigationView;
  private ExtendedFloatingActionButton fab;
  private BadgeDrawable badge;
  private static final int BOTTOM_RESULTS = R.id.main_bottom_results;
  private static final int BOTTOM_COMMUNITY = R.id.main_bottom_community;
  private static final int BOTTOM_ALL_RESULTS = R.id.main_bottom_all_results;
  private NavDestination currentDestination;
  private NavController navController;
  private SearchBar searchBar;
  private SearchView searchView;
  private RecyclerView rv_search;
  private BaseListAdapter<String> searchAdapter;
  private BaseListAdapter<LottoGameBaseModel> gamesAdapter;
  private static final int RESULTS_PROFILE = R.id.results_profile;
  private final Handler handler = new Handler(Looper.getMainLooper());
  private final PostFragment postFragment = new PostFragment();
  private final FragmentDialogVerifyEmail emailDialog = new FragmentDialogVerifyEmail();
  private final DialogFragmentSort sortDialog = new DialogFragmentSort();
  private Target target;
  private AvatarIconMenu avatarIconMenu;
  private NavHostFragment navHostFragment;
  private boolean isSearchViewHidden = true;
  private LinearLayout placeHolder;
  private ImageView ivHolder;
  private TextView tvHolder, tvSubHolder, tvCat;

  private MainActivityViewModel viewModel;
  private ResultsViewModel results_viewModel;
  private AllResultsViewModel all_results_viewModel;
  private FeedViewModel feed_viewModel;

  private RecyclerView rv;

  PrefHelper prefHelper;
  SearchHistoryCache searchHistoryCache;
  UserDataHelper userDataHelper;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();
    System.out.println(appComponent.hashCode());

    this.prefHelper = appComponent.getPrefHelper();
    this.searchHistoryCache = appComponent.getSearchHistoryCache();
    this.userDataHelper = appComponent.getUserDataHelper();
  }

  @Override
  protected MainActivityPresenter createPresenter() {
    return new MainActivityPresenter(this, prefHelper, searchHistoryCache, userDataHelper);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));
    this.binding = ActivityMainBinding.inflate(getLayoutInflater());

    this.viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    this.all_results_viewModel = new ViewModelProvider(this).get(AllResultsViewModel.class);
    this.results_viewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
    this.feed_viewModel = new ViewModelProvider(this).get(FeedViewModel.class);

    setContentView(binding.getRoot());

    onsetViewBinding();
    onsetViews();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.checkEmailVerified();
  }

  @Override
  public void onBackPressed() {
    if (isSearchViewHidden) super.onBackPressed();
    else searchView.hide();
  }

  @Override
  public void onSearchClick(String history) {
    viewModel.setSearch(history);
    searchView.setText(history);
  }

  @Override
  public void onPutTextToSearch(String history) {
    searchView.setText(history);
  }

  @Override
  public void getSearchHistory(List<String> list) {
    searchAdapter.submitList(list);
  }

  @Override
  public void getProfile(String profile) {
    Picasso.get().load(profile).placeholder(R.drawable.profile).into(target);
  }

  @Override
  public void onPause() {
    super.onPause();
    Picasso.get().cancelRequest(target);
  }

  @Override
  public void onItemClick(LottoGameBaseModel model) {
    if (navController.getCurrentDestination().getId() == BOTTOM_RESULTS)
      results_viewModel.setModel(model);
    else if (navController.getCurrentDestination().getId() == BOTTOM_COMMUNITY)
      feed_viewModel.setModel(model);
    else if (navController.getCurrentDestination().getId() == BOTTOM_ALL_RESULTS)
      all_results_viewModel.setModel(model);
    searchView.hide();
    searchBar.setText(model.getName());
  }

  @Override
  public void onEmailVerification(boolean verified, String email) {
    if (!verified) {
      Bundle bundle = new Bundle();
      bundle.putString("email", email);

      emailDialog.setArguments(bundle);
      emailDialog.show(getSupportFragmentManager(), "FragmentDialogVerifyEmail");
    }
  }

  private void onsetViewBinding() {
    this.bottomNavigationView = binding.bottomNavigation;
    this.fab = binding.fab;
    this.searchBar = binding.searchBar;
    this.searchView = binding.searchView;
    this.rv_search = binding.rvSearch;
    this.rv = binding.rvGames;
    this.placeHolder = binding.placeHolder;
    this.tvHolder = binding.tvHolder;
    this.tvSubHolder = binding.tvSubHolder;
    this.ivHolder = binding.ivHolder;
    this.tvCat = binding.tvCat;
  }

  private void onsetViews() {
    onsetGames();
    this.badge = bottomNavigationView.getOrCreateBadge(BOTTOM_COMMUNITY);
    badge.setVisible(false, false);

    this.navHostFragment =
        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    this.navController = navHostFragment.getNavController();

    NavigationUI.setupWithNavController(bottomNavigationView, navController);

    navController.addOnDestinationChangedListener(
        new NavController.OnDestinationChangedListener() {
          @Override
          public void onDestinationChanged(
              @NonNull NavController controller,
              @NonNull NavDestination destination,
              @Nullable Bundle arguments) {
            switch (destination.getId()) {
              case BOTTOM_RESULTS:
                tvHolder.setText("PCSO Hub 2023");
                tvSubHolder.setText(
                    "View the PCSO results this year. Tap the search bar to select a category or search for digits.");
                ivHolder.setImageResource(R.drawable.results_banner);

                searchBar.setHint("Search results");
                fab.hide();
                break;

              case BOTTOM_COMMUNITY:
                tvHolder.setText("Join the community");
                tvSubHolder.setText("Like, Comment and share your luck to the world.");
                ivHolder.setImageResource(R.drawable.social_banner);

                searchBar.setHint("Search usernames");

                fab.show();
                fab.setText(getString(R.string.compose));
                fab.setIconResource(R.drawable.ic_edit);
                fab.setOnClickListener(
                    v -> {
                      postFragment.show(getSupportFragmentManager(), null);
                    });
                break;

              case BOTTOM_ALL_RESULTS:
                tvHolder.setText("PCSO Hub 2013-2022");
                tvSubHolder.setText(
                    "View all PCSO results. Tap the search bar to select a category or search for digits.");
                ivHolder.setImageResource(R.drawable.all_results_banner);

                searchBar.setHint("Search all results");

                fab.show();
                fab.setText(getString(R.string.sort));
                fab.setIconResource(R.drawable.ic_sort);
                fab.setOnClickListener(
                    v -> {
                      sortDialog.show(getSupportFragmentManager(), null);
                    });
                break;
            }

            searchBar.clearText();
            ((GamesAdapter) gamesAdapter).clearCheck();
          }
        });

    onsetSearchBar();
    onsetViewModels();
  }

  private void onsetViewModels() {
    viewModel
        .getFab()
        .observe(
            this,
            show -> {
              if (show) fab.extend();
              else fab.shrink();
            });

    viewModel
        .getBadge()
        .observe(
            this,
            badge -> {
              if (badge == 0) {
                this.badge.setVisible(false, false);
                this.badge.clearNumber();
              } else {
                this.badge.setVisible(true, false);
                this.badge.setNumber(badge);
              }
            });

    viewModel
        .getGames()
        .observe(
            this,
            games -> {
              gamesAdapter.submitList(games);
            });

    viewModel
        .getDialogLogin()
        .observe(
            this,
            dialog -> {
              DialogFragmentLoginPrompt prompt = new DialogFragmentLoginPrompt();
              prompt.show(getSupportFragmentManager(), "DialogFragmentLoginPrompt");
            });

    viewModel
        .getOnProfileUpdated()
        .observe(
            this,
            profile -> {
              presenter.getProfile();
            });

    viewModel
        .getMessage()
        .observe(
            this,
            message -> {
              setMessage(message);
            });

    results_viewModel
        .getJsoupBusy()
        .observe(
            this,
            busy -> {
              if (busy) {
                enableBottomNav(false);
                rv.setVisibility(View.GONE);
                tvCat.setVisibility(View.GONE);
              } else {
                enableBottomNav(true);
                rv.setVisibility(View.VISIBLE);
                tvCat.setVisibility(View.VISIBLE);
              }
            });

    all_results_viewModel
        .getJsonBusy()
        .observe(
            this,
            busy -> {
              if (busy) enableBottomNav(false);
              else enableBottomNav(true);
            });

    // Placeholders
    results_viewModel
        .getPlaceHolder()
        .observe(
            this,
            show -> {
              if (show) placeHolder.setVisibility(View.VISIBLE);
              else placeHolder.setVisibility(View.GONE);
            });

    feed_viewModel
        .getPlaceHolder()
        .observe(
            this,
            show -> {
              if (show) placeHolder.setVisibility(View.VISIBLE);
              else placeHolder.setVisibility(View.GONE);
            });

    all_results_viewModel
        .getPlaceHolder()
        .observe(
            this,
            show -> {
              if (show) placeHolder.setVisibility(View.VISIBLE);
              else placeHolder.setVisibility(View.GONE);
            });
  }

  private void onsetGames() {
    LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    this.gamesAdapter = AdapterFactory.getGamesAdapter(this);
    rv.setLayoutManager(llm);
    rv.setAdapter(gamesAdapter);
  }

  private void onsetSearchBar() {
    searchView.setupWithSearchBar(searchBar);
    searchView
        .getEditText()
        .setOnEditorActionListener(
            (v, actionId, event) -> {
              String sText = searchView.getText().toString();

              searchBar.setText(sText);
              searchView.hide();
              presenter.addSearchHistory(sText);

              if (navController.getCurrentDestination().getId() == BOTTOM_RESULTS)
                results_viewModel.setSearch(sText);
              else if (navController.getCurrentDestination().getId() == BOTTOM_COMMUNITY)
                feed_viewModel.setSearch(sText);
              else if (navController.getCurrentDestination().getId() == BOTTOM_ALL_RESULTS)
                all_results_viewModel.setSearch(sText);

              return true;
            });

    searchView.addTransitionListener(
        (searchView, previousState, newState) -> {
          presenter.getSearchHistory();

          if (navController.getCurrentDestination().getId() == BOTTOM_COMMUNITY
              || navController.getCurrentDestination().getId() == BOTTOM_ALL_RESULTS) {
            if (newState == SearchView.TransitionState.SHOWING) {
              fab.hide();
              getWindow().setStatusBarColor(SurfaceColors.SURFACE_3.getColor(this));
              getWindow().setNavigationBarColor(SurfaceColors.SURFACE_3.getColor(this));
            } else if (previousState == SearchView.TransitionState.HIDING) {
              getWindow().setStatusBarColor(SurfaceColors.SURFACE_2.getColor(this));
              getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));
              fab.show();
            }
          }

          if (newState == SearchView.TransitionState.SHOWING) {
            isSearchViewHidden = false;
            getWindow().setStatusBarColor(SurfaceColors.SURFACE_3.getColor(this));
            getWindow().setNavigationBarColor(SurfaceColors.SURFACE_3.getColor(this));
          } else if (previousState == SearchView.TransitionState.HIDING) {
            isSearchViewHidden = true;
            getWindow().setStatusBarColor(SurfaceColors.SURFACE_0.getColor(this));
            getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));
          }
        });

    InputFilter[] filters = new InputFilter[1];
    filters[0] = new InputFilter.LengthFilter(17);
    searchView.getEditText().setFilters(filters);

    LinearLayoutManager llm = new LinearLayoutManager(this);
    this.searchAdapter = AdapterFactory.getSearchHistoryAdapter(this);
    rv_search.setLayoutManager(llm);
    rv_search.setAdapter(searchAdapter);

    this.avatarIconMenu = new AvatarIconMenu(searchBar, RESULTS_PROFILE);

    this.target = avatarIconMenu.setAvatarrIcon();

    searchBar.inflateMenu(R.menu.results_menu);
    searchBar.setOnMenuItemClickListener(
        menu -> {
          switch (menu.getItemId()) {
            case RESULTS_PROFILE:
              startActivity(new Intent(this, MainPreferenceActivity.class));
              return true;
          }
          return false;
        });

    presenter.getProfile();
  }

  private void setMessage(String message) {
    Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
        .setAnchorView(bottomNavigationView)
        .show();
  }

  private void enableBottomNav(boolean enable) {
    Menu menu = bottomNavigationView.getMenu();
    MenuItem res_item = menu.findItem(BOTTOM_RESULTS);
    MenuItem feed_item = menu.findItem(BOTTOM_COMMUNITY);
    MenuItem allres_item = menu.findItem(BOTTOM_ALL_RESULTS);

    res_item.setEnabled(enable);
    feed_item.setEnabled(enable);
    allres_item.setEnabled(enable);
  }
}
