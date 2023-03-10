package com.ph.pcsolottowatcher.di;

import com.ph.pcsolottowatcher.data.firebase.FeedDataHelper;
import com.ph.pcsolottowatcher.di.scopes.PerActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent
public interface FeedComponent {
  @Subcomponent.Factory
  interface Factory {
    FeedComponent create();
  }

  FeedDataHelper getFeedDataHelper();
}
