package com.ph.pcsolottowatcher.recyclerview.holder.results.threetime;

import com.ph.pcsolottowatcher.databinding.ThreeTimeLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.base.results.ThreeTimeBaseViewHolder;

public class ThreeTimeLatestViewHolder extends ThreeTimeBaseViewHolder {

  public ThreeTimeLatestViewHolder(ThreeTimeLatestItemBinding binding) {
    super(binding.getRoot());
    this.tvDate = binding.tvDate;
    this.tvTimeOne = binding.tvTimeOne;
    this.tvTimeTwo = binding.tvTimeTwo;
    this.tvTimeThree = binding.tvTimeThree;
    this.circViewOne = binding.circularItemTimeOne;
    this.circViewTwo = binding.circularItemTimeTwo;
    this.circViewThree = binding.circularItemTimeThree;
  }

  @Override
  public void bind(ThreeTimeHistoryModel model) {
    super.bind(model);
    tvDate.setText(model.getDate());

    circViewOne.setText(timeOneResultText);
    circViewTwo.setText(timeTwoResultText);
    circViewThree.setText(timeThreeResultText);
  }
}
