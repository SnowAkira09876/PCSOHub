package com.ph.pcsolottowatcher.recyclerviews.holder.results.sixdigits;

import com.ph.pcsolottowatcher.databinding.SixDigitsHistoryItemBinding;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.base.results.SixDigitsBaseViewHolder;

public class SixDigitsHistoryViewHolder extends SixDigitsBaseViewHolder {

  public SixDigitsHistoryViewHolder(SixDigitsHistoryItemBinding binding) {
    super(binding.getRoot());
    this.circView = binding.circularItem;
    this.tvDate = binding.tvDate;
    this.tvPrize = binding.tvPrize;
  }

  @Override
  public void bind(SixDigitsHistoryModel model) {
    super.bind(model);

    int indexDate =
        model.getDate().indexOf(".") >= 0
            ? model.getDate().indexOf(".")
            : model.getDate().indexOf(",");

    if (indexDate >= 0) {
      String dateWithoutPeriod = model.getDate().substring(0, indexDate);
      tvDate.setText(dateWithoutPeriod);
    }

    circView.setText(numberText);
  }
}
