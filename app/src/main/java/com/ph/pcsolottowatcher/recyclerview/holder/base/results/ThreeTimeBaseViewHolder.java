package com.ph.pcsolottowatcher.recyclerview.holder.base.results;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;

public class ThreeTimeBaseViewHolder extends RecyclerView.ViewHolder {
  protected TextView tvDay, tvMonth, tvDate, tvTimeOne, tvTimeTwo, tvTimeThree;
  protected CircularDigitView circViewOne, circViewTwo, circViewThree;
  protected String[] timeOneResultText, timeTwoResultText, timeThreeResultText;
  protected Context context;
  protected ThreeTimeHistoryModel model;
  protected int count = 0;

  public ThreeTimeBaseViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
  }

  public void bind(ThreeTimeHistoryModel model) {
    this.model = model;
    this.timeOneResultText =
        model.getTimeOneResult().length() > 1
            ? model.getTimeOneResult().split("-")
            : new String[] {"?", "?"};
    this.timeTwoResultText =
        model.getTimeTwoResult().length() > 1
            ? model.getTimeTwoResult().split("-")
            : new String[] {"?", "?"};
    this.timeThreeResultText =
        model.getTimeThreeResult().length() > 1
            ? model.getTimeThreeResult().split("-")
            : new String[] {"?", "?"};

    tvTimeOne.setText(model.getTimeOne());
    tvTimeTwo.setText(model.getTimeTwo());
    tvTimeThree.setText(model.getTimeThree());
  }
}
