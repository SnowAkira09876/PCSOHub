package com.ph.pcsolottowatcher.recyclerview.holder.base.results;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.R;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;

public class SixDigitsBaseViewHolder extends RecyclerView.ViewHolder {
  protected Context context;
  protected CircularDigitView circView;
  protected TextView tvDate, tvPrize;
  protected String[] numberText;
  protected SixDigitsHistoryModel model;
  protected int count = 0;

  public SixDigitsBaseViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
  }

  protected void bind(SixDigitsHistoryModel model) {
    this.model = model;
    String prize_format = context.getString(R.string.prize);
    String prizeText = model.getPrize().length() > 1 ? model.getPrize() : "";

    this.numberText =
        model.getNumber().contains("-")
            ? model.getNumber().split("-")
            : new String[] {"?", "?", "?", "?", "?", "?"};

    tvDate.setText(model.getDate());
    tvPrize.setText(String.format(prize_format, prizeText));
  }
}
