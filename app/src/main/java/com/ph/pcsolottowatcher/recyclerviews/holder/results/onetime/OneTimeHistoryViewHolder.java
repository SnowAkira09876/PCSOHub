package com.ph.pcsolottowatcher.recyclerviews.holder.results.onetime;

import com.ph.pcsolottowatcher.databinding.OneTimeHistoryItemBinding;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.base.results.OneTimeBaseViewHolder;

public class OneTimeHistoryViewHolder extends OneTimeBaseViewHolder {

  public OneTimeHistoryViewHolder(OneTimeHistoryItemBinding binding) {
    super(binding.getRoot());
    this.tvDate = binding.tvDate;
    this.circView = binding.circularItem;
  }

  @Override
  public void bind(OneTimeHistoryModel model) {
    super.bind(model);
    circView.setText(timeOneResultText);
  }
}
