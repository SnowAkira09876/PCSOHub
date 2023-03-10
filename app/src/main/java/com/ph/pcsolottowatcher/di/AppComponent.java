package com.ph.pcsolottowatcher.di;

import com.ph.pcsolottowatcher.bottomnav.tab3.AllResultsFragment;
import com.ph.pcsolottowatcher.data.ExecutorHelper;
import com.ph.pcsolottowatcher.data.firebase.UserDataHelper;
import com.ph.pcsolottowatcher.data.json.JsonHelper;
import com.ph.pcsolottowatcher.data.jsoup.results.DParser;
import com.ph.pcsolottowatcher.data.jsoup.results.OneTimeParser;
import com.ph.pcsolottowatcher.data.jsoup.results.SixDigitsParser;
import com.ph.pcsolottowatcher.data.jsoup.results.ThreeTimeParser;
import com.ph.pcsolottowatcher.data.sharedpref.PrefHelper;
import com.ph.pcsolottowatcher.data.sql.cache.DHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.OneTimeHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.SixDigitsHistoryCache;
import com.ph.pcsolottowatcher.data.sql.cache.ThreeTimeHistoryCache;
import com.ph.pcsolottowatcher.data.sql.post.PostCache;
import com.ph.pcsolottowatcher.data.sql.search.SearchHistoryCache;
import com.ph.pcsolottowatcher.di.modules.DataCoreModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {DataCoreModule.class})
public interface AppComponent {

  // Singleton Instances

  JsonHelper getJsonHelper();

  PostCache getPostCache();

  PrefHelper getPrefHelper();

  SearchHistoryCache getSearchHistoryCache();

  DHistoryCache getDHistoryCache();

  SixDigitsHistoryCache getSixDigitsHistoryCache();

  OneTimeHistoryCache getOneTimeHistoryCache();

  ThreeTimeHistoryCache getThreeTimeHistoryCache();

  ExecutorHelper getExecutorHelper();

  UserDataHelper getUserDataHelper();

  SixDigitsParser getSixDigitsParser();

  DParser getDParser();

  OneTimeParser getOneTimeParser();

  ThreeTimeParser getThreeTimeParser();

  // Singleton Instances

  FeedComponent.Factory getFeedComponent();

  void inject(AllResultsFragment view);
}
