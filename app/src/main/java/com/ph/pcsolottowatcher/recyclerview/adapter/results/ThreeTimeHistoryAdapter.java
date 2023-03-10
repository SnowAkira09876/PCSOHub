package com.ph.pcsolottowatcher.recyclerview.adapter.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.databinding.ThreeTimeHistoryItemBinding;
import com.ph.pcsolottowatcher.databinding.ThreeTimeLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.threetime.ThreeTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;
import com.ph.pcsolottowatcher.recyclerview.holder.results.threetime.ThreeTimeHistoryViewHolder;
import com.ph.pcsolottowatcher.recyclerview.holder.results.threetime.ThreeTimeLatestViewHolder;

public class ThreeTimeHistoryAdapter extends RootListAdapter<ThreeTimeHistoryModel> {
  private static final int THREE_TIME_LATEST_ITEM = 0;
  private static final int THREE_TIME_HISTORY_ITEM = 1;

  public ThreeTimeHistoryAdapter(DiffUtil.ItemCallback<ThreeTimeHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case THREE_TIME_LATEST_ITEM:
        return new ThreeTimeLatestViewHolder(
            ThreeTimeLatestItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      case THREE_TIME_HISTORY_ITEM:
        return new ThreeTimeHistoryViewHolder(
            ThreeTimeHistoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int viewType = getItemViewType(position);

    switch (viewType) {
      case THREE_TIME_LATEST_ITEM:
        ThreeTimeLatestViewHolder viewHolder0 = (ThreeTimeLatestViewHolder) holder;
        ThreeTimeHistoryModel model0 = getItem(position);

        viewHolder0.bind(model0);
        break;

      case THREE_TIME_HISTORY_ITEM:
        ThreeTimeHistoryViewHolder viewHolder1 = (ThreeTimeHistoryViewHolder) holder;
        ThreeTimeHistoryModel model1 = getItem(position);

        viewHolder1.bind(model1);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) return THREE_TIME_LATEST_ITEM;
    else return THREE_TIME_HISTORY_ITEM;
  }
}
