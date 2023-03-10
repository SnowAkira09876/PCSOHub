package com.ph.pcsolottowatcher.widgets.avataricon;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.Toolbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class AvatarIconMenu {
  private Toolbar toolbar;
  private int action_menu;

  public AvatarIconMenu(Toolbar toolbar, int action_menu) {
    this.toolbar = toolbar;
    this.action_menu = action_menu;
  }

  public Target setAvatarrIcon() {
    Target target =
        new Target() {
          @Override
          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Bitmap circularBitmap = getCircularBitmap(bitmap);
            BitmapDrawable circularDrawable =
                new BitmapDrawable(toolbar.getContext().getResources(), circularBitmap);

            toolbar.getMenu().findItem(action_menu).setIcon(circularDrawable);
          }

          @Override
          public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            // Handle the error
          }

          @Override
          public void onPrepareLoad(Drawable placeHolderDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) placeHolderDrawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();

            Bitmap circularBitmap = getCircularBitmap(bitmap);
            BitmapDrawable circularDrawable =
                new BitmapDrawable(toolbar.getContext().getResources(), circularBitmap);

            toolbar.getMenu().findItem(action_menu).setIcon(circularDrawable);
          }
        };
    return target;
  }

  private Bitmap getCircularBitmap(Bitmap bitmap) {
    int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
    Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(output);
    Paint paint = new Paint();
    paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    paint.setAntiAlias(true);

    float radius = size / 2f;
    canvas.drawCircle(radius, radius, radius, paint);

    return output;
  }
}
