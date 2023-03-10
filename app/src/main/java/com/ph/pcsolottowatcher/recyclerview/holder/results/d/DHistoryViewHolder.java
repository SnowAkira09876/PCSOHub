package com.ph.pcsolottowatcher.recyclerview.holder.results.d;

import com.ph.pcsolottowatcher.databinding.DHistoryItemBinding;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.base.results.DBaseViewHolder;

public class DHistoryViewHolder extends DBaseViewHolder {

  public DHistoryViewHolder(DHistoryItemBinding binding) {
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
