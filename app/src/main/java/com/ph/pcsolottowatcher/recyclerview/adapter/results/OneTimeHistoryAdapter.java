package com.ph.pcsolottowatcher.recyclerview.adapter.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.databinding.OneTimeHistoryItemBinding;
import com.ph.pcsolottowatcher.databinding.OneTimeLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.onetime.OneTimeHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;
import com.ph.pcsolottowatcher.recyclerview.holder.results.onetime.OneTimeHistoryViewHolder;
import com.ph.pcsolottowatcher.recyclerview.holder.results.onetime.OneTimeLatestViewHolder;

public class OneTimeHistoryAdapter extends RootListAdapter<OneTimeHistoryModel> {
  private static final int ONE_TIME_LATEST_ITEM = 0;
  private static final int ONE_TIME_HISTORY_ITEM = 1;

  public OneTimeHistoryAdapter(DiffUtil.ItemCallback<OneTimeHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ONE_TIME_LATEST_ITEM:
        return new OneTimeLatestViewHolder(
            OneTimeLatestItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      case ONE_TIME_HISTORY_ITEM:
        return new OneTimeHistoryViewHolder(
            OneTimeHistoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int viewType = getItemViewType(position);

    switch (viewType) {
      case ONE_TIME_LATEST_ITEM:
        OneTimeLatestViewHolder viewHolder0 = (OneTimeLatestViewHolder) holder;
        OneTimeHistoryModel model0 = getItem(position);

        viewHolder0.bind(model0);
        break;

      case ONE_TIME_HISTORY_ITEM:
        OneTimeHistoryViewHolder viewHolder1 = (OneTimeHistoryViewHolder) holder;
        OneTimeHistoryModel model1 = getItem(position);

        viewHolder1.bind(model1);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) return ONE_TIME_LATEST_ITEM;
    else return ONE_TIME_HISTORY_ITEM;
  }
}
