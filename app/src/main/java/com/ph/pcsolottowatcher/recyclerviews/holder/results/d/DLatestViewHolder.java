package com.ph.pcsolottowatcher.recyclerviews.holder.results.d;

import com.ph.pcsolottowatcher.databinding.DLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.base.results.DBaseViewHolder;

public class DLatestViewHolder extends DBaseViewHolder {

  public DLatestViewHolder(DLatestItemBinding binding) {
    super(binding.getRoot());
    this.circView = binding.circularItem;
    this.tvDate = binding.tvDate;
  }

  @Override
  public void bind(DHistoryModel model) {
    super.bind(model);
    circView.setText(numberText);
  }
}
