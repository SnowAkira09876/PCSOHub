package com.ph.pcsolottowatcher.recyclerview.holder.results.threetime;

import com.ph.pcsolottowatcher.databinding.ThreeTimeHistoryItemBinding;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.base.results.ThreeTimeBaseViewHolder;

public class ThreeTimeHistoryViewHolder extends ThreeTimeBaseViewHolder {

  public ThreeTimeHistoryViewHolder(ThreeTimeHistoryItemBinding binding) {
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
    int indexDate =
        model.getDate().indexOf(".") >= 0
            ? model.getDate().indexOf(".")
            : model.getDate().indexOf(",");

    if (indexDate >= 0) {
      String dateWithoutPeriod = model.getDate().substring(0, indexDate);
      tvDate.setText(dateWithoutPeriod);
    }

    circViewOne.setText(timeOneResultText);
    circViewTwo.setText(timeTwoResultText);
    circViewThree.setText(timeThreeResultText);
  }
}
