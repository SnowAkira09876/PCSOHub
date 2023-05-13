package com.ph.pcsolottowatcher.recyclerviews.adapter.allresults;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.AllHistoryItemBinding;
import com.ph.pcsolottowatcher.databinding.AllHistoryLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.allresults.AllHistoryLatestViewHolder;
import com.ph.pcsolottowatcher.recyclerviews.holder.allresults.AllHistoryViewHolder;

public class AllResultsAdapter extends BaseListAdapter<LocalHistoryModel> {
  private static final int ALL_HISTORY_LATEST_ITEM = 0;
  private static final int ALL_HISTORY_ITEM = 1;

  public AllResultsAdapter(DiffUtil.ItemCallback<LocalHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case 0:
        return new AllHistoryLatestViewHolder(
            AllHistoryLatestItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
      case 1:
        return new AllHistoryViewHolder(
            AllHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case ALL_HISTORY_LATEST_ITEM:
        AllHistoryLatestViewHolder viewHolder = (AllHistoryLatestViewHolder) holder;
        LocalHistoryModel model = getItem(position);

        viewHolder.bind(model);
        break;
      case ALL_HISTORY_ITEM:
        AllHistoryViewHolder viewHolder1 = (AllHistoryViewHolder) holder;
        LocalHistoryModel model1 = getItem(position);

        viewHolder1.bind(model1);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) return ALL_HISTORY_LATEST_ITEM;
    else return ALL_HISTORY_ITEM;
  }
}
