package com.ph.pcsolottowatcher.recyclerviews.holder.base.results;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;

public class OneTimeBaseViewHolder extends RecyclerView.ViewHolder {
  protected TextView tvDate, tvTimeOneResult;
  protected String[] timeOneResultText;
  protected Context context;
  protected OneTimeHistoryModel model;
  protected int count = 0;
  protected CircularDigitView circView;

  public OneTimeBaseViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
  }

  protected void bind(OneTimeHistoryModel model) {
    this.model = model;
    this.timeOneResultText =
        model.getTimeOne().contains("-")
            ? model.getTimeOneResult().split("-")
            : new String[] {"?", "?", "?", "?"};

    int index =
        model.getDate().indexOf(".") >= 0
            ? model.getDate().indexOf(".")
            : model.getDate().indexOf(",");

    if (index >= 0) {
      String dateWithoutPeriod = model.getDate().substring(0, index);
      tvDate.setText(dateWithoutPeriod);
    }
  }
}
