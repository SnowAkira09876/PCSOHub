package com.ph.pcsolottowatcher.preference;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.ph.pcsolottowatcher.BuildConfig;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.StartApplication;
import com.ph.pcsolottowatcher.common.preference.BasePreference;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.di.AppComponent;
import com.ph.pcsolottowatcher.dialogs.clearcache.DialogFragmentClearCache;
import com.ph.pcsolottowatcher.dialogs.deleteaccount.DialogFragmentDeleteAcc;
import com.ph.pcsolottowatcher.dialogs.logoutprompt.DialogFragmentLogoutPrompt;
import com.ph.pcsolottowatcher.dialogs.themepicker.DialogFragmentTheme;
import com.ph.pcsolottowatcher.dialogs.verifyemail.FragmentDialogVerifyEmail;
import com.ph.pcsolottowatcher.viewmodels.MainPreferenceViewModel;
import com.ph.pcsolottowatcher.viewmodels.MainSettingsFragmentViewModel;

public class MainSettingsFragment extends BasePreference<MainSettingsFragmentPresenter>
    implements MainSettingsFragmentView {

  private ActivityResultLauncher<Intent> signInLauncher =
      registerForActivityResult(
          new FirebaseAuthUIActivityResultContract(),
          (result) -> {
            IdpResponse response = result.getIdpResponse();
            if (result.getResultCode() == Activity.RESULT_OK) {
              setMessage("Successfully signed in");
              presenter.sendEmailVerification();
            } else {
              if (response == null) {
                setMessage("Cancelled");
                return;
              }
              if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                setMessage("Can't connect to internet");
                return;
              }
              setMessage("Something went wrong");
            }
          });
  private ActivityResultLauncher<String> imagePicker =
      registerForActivityResult(
          new ActivityResultContracts.GetContent(),
          new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
              if (uri != null) {
                presenter.uploadProfile(uri, "profile" + "." + getFileExtension(uri));
              }
            }
          });
  private final FragmentDialogVerifyEmail emailDialog = new FragmentDialogVerifyEmail();
  private MainPreferenceViewModel viewModel;
  private MainSettingsFragmentViewModel settingsViewModel;

  PrefHelper prefHelper;

  UserDataHelper userDataHelper;

  @Override
  protected void injectDependencies() {
    AppComponent appComponent = StartApplication.getComponent();
    this.userDataHelper = appComponent.getUserDataHelper();
    this.prefHelper = appComponent.getPrefHelper();
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.main_settings, rootKey);

    onsetViews();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.viewModel = new ViewModelProvider(getActivity()).get(MainPreferenceViewModel.class);
    this.settingsViewModel =
        new ViewModelProvider(getActivity()).get(MainSettingsFragmentViewModel.class);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.getInfo();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override
  protected MainSettingsFragmentPresenter createPresenter() {
    return new MainSettingsFragmentPresenter(this, prefHelper, userDataHelper);
  }

  private void onsetViews() {
    findPreference("pref_theme_key")
        .setOnPreferenceClickListener(
            preference -> {
              new DialogFragmentTheme().show(getParentFragmentManager(), null);
              return true;
            });

    findPreference("pref_dynamic_key")
        .setOnPreferenceChangeListener(
            (preference, newValue) -> {
              if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                viewModel.setMessage("Device is not supported for Dynamic theming");
              } else presenter.setDynamicTheme("pref_dynamic_key", (Boolean) newValue);
              return true;
            });

    findPreference("pref_changelogs")
        .setOnPreferenceClickListener(
            preference -> {
              viewModel.setMessage("Coming soon!");
              return true;
            });
    findPreference("pref_clear_cache")
        .setOnPreferenceClickListener(
            preference -> {
              new DialogFragmentClearCache().show(getParentFragmentManager(), null);
              return true;
            });

    findPreference("pref_sign_in_key")
        .setOnPreferenceClickListener(
            preference -> {
              Intent signInIntent =
                  AuthUI.getInstance()
                      .createSignInIntentBuilder()
                      .setTheme(R.style.AppTheme)
                      .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
                      .build();

              signInLauncher.launch(signInIntent);
              return true;
            });

    findPreference("pref_dispic_key")
        .setOnPreferenceClickListener(
            preference -> {
              imagePicker.launch("image/*");
              return true;
            });

    findPreference("pref_sign_out_key")
        .setOnPreferenceClickListener(
            logout -> {
              new DialogFragmentLogoutPrompt().show(getParentFragmentManager(), null);
              return true;
            });

    findPreference("pref_delete_acc_key")
        .setOnPreferenceClickListener(
            logout -> {
              new DialogFragmentDeleteAcc().show(getParentFragmentManager(), null);
              return true;
            });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    return super.onCreateView(inflater, parent, savedInstanceState);
  }

  @Override
  public void getInfo(String name, String email) {
    PreferenceCategory category = (PreferenceCategory) findPreference("cat_account_key");
    category.setTitle(name);

    Preference signInPref = findPreference("pref_sign_in_key");
    signInPref.setSummary(email);
  }

  @Override
  public void onUploading(int progress, boolean uploading) {
    if (uploading) {
      viewModel.setMessage("Don't exit this page while uploading " + progress + "%");
    } else {
      viewModel.setMessage("Uploaded successfully");
    }
  }

  @Override
  public void setMessage(String message) {
    viewModel.setMessage(message);
  }

  @Override
  public void onEmailVerification(boolean verified, String email) {
    if (!verified) {
      Bundle bundle = new Bundle();
      bundle.putString("email", email);

      emailDialog.setArguments(bundle);
      emailDialog.show(getParentFragmentManager(), "FragmentDialogVerifyEmail");
    }
  }

  private String getFileExtension(Uri uri) {
    ContentResolver cR = getActivity().getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
  }
}
