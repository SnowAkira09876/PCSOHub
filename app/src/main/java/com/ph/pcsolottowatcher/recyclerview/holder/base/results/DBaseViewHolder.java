package com.ph.pcsolottowatcher.recyclerview.holder.base.results;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;

public class DBaseViewHolder extends RecyclerView.ViewHolder {
  protected TextView tvNumber, tvDate, tvPrize;
  protected String[] numberText;
  protected Context context;
  protected DHistoryModel model;
  protected CircularDigitView circView;

  public DBaseViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
  }

  protected void bind(DHistoryModel model) {
    this.numberText =
        model.getNumber().contains("-")
            ? model.getNumber().split("-")
            : new String[] {"?", "?", "?"};

    this.model = model;

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
