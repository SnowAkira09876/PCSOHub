package com.ph.pcsolottowatcher.recyclerview.holder.results.onetime;

import com.ph.pcsolottowatcher.databinding.OneTimeLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.base.results.OneTimeBaseViewHolder;

public class OneTimeLatestViewHolder extends OneTimeBaseViewHolder {

  public OneTimeLatestViewHolder(OneTimeLatestItemBinding binding) {
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
