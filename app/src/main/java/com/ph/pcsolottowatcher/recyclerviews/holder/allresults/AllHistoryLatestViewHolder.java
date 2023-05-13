package com.ph.pcsolottowatcher.recyclerviews.holder.allresults;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.databinding.AllHistoryLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.widgets.circular.CircularDigitView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AllHistoryLatestViewHolder extends RecyclerView.ViewHolder {
  private TextView tvDay, tvMonth, tvNumber, tvPrize, tvWinners, tvYear;
  private CircularDigitView circView;

  public AllHistoryLatestViewHolder(AllHistoryLatestItemBinding binding) {
    super(binding.getRoot());
    this.tvMonth = binding.tvMonth;
    this.tvDay = binding.tvDay;
    this.tvYear = binding.tvYear;
    this.tvWinners = binding.tvWinners;
    this.tvPrize = binding.tvPrize;
    this.circView = binding.circularItem;
  }

  public void bind(LocalHistoryModel model) {
    String formatted_date;

    SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    try {
      formatted_date =
          new SimpleDateFormat("MMM, dd, yyyy", Locale.US)
              .format(inputFormat.parse(model.getDate()));
    } catch (ParseException e) {
      formatted_date = "Unknown date";
    }

    String[] split_date = formatted_date.split(",");
    String[] numberText = model.getNumber().split("-");

    this.tvMonth.setText(split_date[0]);
    this.tvDay.setText(split_date[1]);
    this.tvYear.setText(split_date[2]);
    this.tvWinners.setText(model.getWinners());
    this.tvPrize.setText(model.getPrize());
    this.circView.setText(numberText);
  }
}
