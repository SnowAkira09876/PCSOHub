package com.ph.pcsolottowatcher.activities.preference;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.activities.main.MainActivity;
import com.ph.pcsolottowatcher.common.activity.BaseActivity;
import com.ph.pcsolottowatcher.common.preference.BasePreference;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.databinding.ActivityPreferenceBinding;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.preference.MainSettingsFragment;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainSettingsFragmentViewModel;

public class MainPreferenceActivity extends BaseActivity<MainPreferencePresenter>
    implements MainPreferenceView {
  private ActivityPreferenceBinding binding;
  private BasePreference mainSettingsFragment;
  private FragmentManager fragmentManager;
  private MainPreferenceViewModel viewModel;
  private MainSettingsFragmentViewModel settingsViewModel;
  private AppComponent appComponent;
  PrefHelper prefHelper;

  @Override
  protected void injectDependencies() {
    this.appComponent = StartApplication.getComponent();

    this.prefHelper = appComponent.getPrefHelper();
  }

  @Override
  protected MainPreferencePresenter createPresenter() {
    return new MainPreferencePresenter(this, prefHelper);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.binding = ActivityPreferenceBinding.inflate(getLayoutInflater());
    this.mainSettingsFragment = new MainSettingsFragment();
    this.fragmentManager = getSupportFragmentManager();
    this.viewModel = new ViewModelProvider(this).get(MainPreferenceViewModel.class);
    this.settingsViewModel = new ViewModelProvider(this).get(MainSettingsFragmentViewModel.class);

    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    fragmentManager
        .beginTransaction()
        .setReorderingAllowed(true)
        .replace(R.id.preference_container, mainSettingsFragment, null)
        .commit();

    viewModel
        .getMessage()
        .observe(
            this,
            message -> {
              setMessage(message);
            });
  }

  @Override
  public boolean onSupportNavigateUp() {
    if (getSupportFragmentManager().popBackStackImmediate()) {
      return true;
    }
    supportNavigateUpTo(
        new Intent(this, MainActivity.class));
    return super.onSupportNavigateUp();
  }

  @Override
  public void onBackPressed() {
    if (getSupportFragmentManager().popBackStackImmediate()) {
      return;
    }
    startActivity(new Intent(this, MainActivity.class));
  }

  @Override
  public void setMessage(String message) {
    Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
  }
}
