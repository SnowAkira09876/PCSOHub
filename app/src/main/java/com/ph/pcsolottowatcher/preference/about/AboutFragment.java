package com.ph.pcsolottowatcher.preference.about;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.ph.pcsolottowatcher.R;

public class AboutFragment extends MaterialAboutFragment {

  @Override
  protected MaterialAboutList getMaterialAboutList(Context context) {
    MaterialAboutCard appCard =
        new MaterialAboutCard.Builder()
            .addItem(ConvenienceBuilder.createAppTitleItem(context))
            .addItem(
                new MaterialAboutActionItem.Builder()
                    .subText(getString(R.string.introduction))
                    .build())
            .addItem(
                ConvenienceBuilder.createVersionActionItem(
                    context,
                    getDrawable(R.drawable.ic_information),
                    getString(R.string.version),
                    true))
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_github),
                    getString(R.string.source_code),
                    false,
                    Uri.parse("https://github.com/SnowAkira09876/PCSOHub")))
            .addItem(
                ConvenienceBuilder.createEmailItem(
                    context,
                    getDrawable(R.drawable.ic_email),
                    getString(R.string.email),
                    false,
                    getString(R.string.email_address),
                    ""))
            .addItem(
                ConvenienceBuilder.createRateActionItem(
                    context, getDrawable(R.drawable.ic_rate), getString(R.string.rate_us), null))
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.flaticon),
                    getString(R.string.flaticon),
                    false,
                    Uri.parse("https://www.flaticon.com")))
            .build();

    MaterialAboutCard devCard =
        new MaterialAboutCard.Builder()
            .title(getString(R.string.developer))
            .addItem(
                new MaterialAboutActionItem.Builder()
                    .text(getString(R.string.akira_snow))
                    .subText(getString(R.string.who_am_i))
                    .build())
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_facebook),
                    getString(R.string.facebook),
                    false,
                    Uri.parse("https://www.facebook.com/profile.php?id=100087796637987")))
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_twitter),
                    getString(R.string.twitter),
                    false,
                    Uri.parse("https://twitter.com/snowakira2814?s=09")))
            .build();

    MaterialAboutCard privacyCard =
        new MaterialAboutCard.Builder()
            .title(getString(R.string.terms_and_privacy_title))
            .addItem(
                new MaterialAboutActionItem.Builder()
                    .text(getString(R.string.disclaimer_title))
                    .subText(getString(R.string.disclaimer))
                    .build())
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_lock_outline),
                    getString(R.string.privacy_policy_title),
                    false,
                    Uri.parse(
                        "https://www.freeprivacypolicy.com/live/c48a3140-386e-4686-a944-118fe1d52873")))
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_web),
                    getString(R.string.source),
                    false,
                    Uri.parse("https://www.lottopcso.com")))
            .build();

    return new MaterialAboutList.Builder()
        .addCard(appCard)
        .addCard(devCard)
        .addCard(privacyCard)
        .build();
  }

  private Drawable getDrawable(@DrawableRes int drawable) {
    return ResourcesCompat.getDrawable(
        requireContext().getResources(), drawable, requireContext().getTheme());
  }
}
