package com.ph.pcsolottowatcher.recyclerviews.holder.results.sixdigits;

import com.ph.pcsolottowatcher.databinding.SixDigitsLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.base.results.SixDigitsBaseViewHolder;

public class SixDigitsLatestViewHolder extends SixDigitsBaseViewHolder {

  public SixDigitsLatestViewHolder(SixDigitsLatestItemBinding binding) {
    super(binding.getRoot());
    this.circView = binding.circularItem;
    this.tvDate = binding.tvDate;
    this.tvPrize = binding.tvPrize;
  }

  @Override
  public void bind(SixDigitsHistoryModel model) {
    super.bind(model);
    circView.setText(numberText);
  }
}
