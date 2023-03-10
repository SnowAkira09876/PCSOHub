package com.ph.pcsolottowatcher.widgets.circular;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

@SuppressWarnings("deprecation")
public class CircularDrawable extends Drawable {
  private Paint paint;
  private int color;

  public CircularDrawable(int color) {
    this.color = color;
    paint = new Paint();
    paint.setColor(color);
    paint.setAntiAlias(true);
  }

  @Override
  public void draw(Canvas canvas) {
    int width = getBounds().width();
    int height = getBounds().height();
    int radius = Math.min(width, height) / 2;
    canvas.drawCircle(width / 2, height / 2, radius, paint);
  }

  @Override
  public void setAlpha(int alpha) {
    paint.setAlpha(alpha);
  }

  @Override
  public void setColorFilter(ColorFilter colorFilter) {
    paint.setColorFilter(colorFilter);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }
}
