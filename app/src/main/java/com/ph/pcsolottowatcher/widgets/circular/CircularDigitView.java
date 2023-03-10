package com.ph.pcsolottowatcher.widgets.circular;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.ph.pcsolottowatcher.R;

public class CircularDigitView extends LinearLayout {

  private AppCompatTextView[] textViews;
  private Context context;
  private AttributeSet attrs;
  private TypedArray typedArray;
  private CircularDrawable circDrawable;
  private int color, size, textAppearance, textColor;

  public CircularDigitView(Context context) {
    super(context);
  }

  public CircularDigitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CircularDigitView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    this.attrs = attrs;
    this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularDigitView);
    this.size = typedArray.getDimensionPixelSize(R.styleable.CircularDigitView_circ_size, 30);

    this.color =
        typedArray.getColor(R.styleable.CircularDigitView_circ_backgroundColor, Color.YELLOW);
    this.textAppearance =
        typedArray.getResourceId(
            R.styleable.CircularDigitView_circ_textAppearance, android.R.attr.textAppearance);
    this.textColor = typedArray.getColor(R.styleable.CircularDigitView_circ_textColor, Color.WHITE);
    typedArray.recycle();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  public void setText(@NonNull String[] texts) {
    setLayout(texts.length);

    for (int i = 0; i < texts.length; i++) {
      textViews[i].setText(texts[i]);
    }
  }

  private void setLayout(@IntRange(from = 0, to = 6) int circCount) {
    this.circDrawable = new CircularDrawable(color);
    this.textViews = new AppCompatTextView[circCount];

    removeAllViews();
    setOrientation(HORIZONTAL);

    // Adding Circular Layouts
    for (int i = 0; i < circCount; i++) {
      LinearLayout linearLayout = new LinearLayout(context);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);

      linearLayout.setLayoutParams(layoutParams);
      linearLayout.setBackground(circDrawable);
      linearLayout.setGravity(Gravity.CENTER);
      layoutParams.setMargins(4, 4, 4, 4);

      textViews[i] = new AppCompatTextView(context);
      textViews[i].setLayoutParams(
          new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

      linearLayout.addView(textViews[i]);
      addView(linearLayout);
    }

    // Setting texts
    for (int i = 0; i < circCount; i++) {
      if (textAppearance != -1) {
        textViews[i].setTextAppearance(context, textAppearance);
        textViews[i].setTextColor(textColor);
      }
    }
  }
}
