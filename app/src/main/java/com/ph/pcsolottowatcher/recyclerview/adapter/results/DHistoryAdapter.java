package com.ph.pcsolottowatcher.recyclerview.adapter.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.DHistoryItemBinding;
import com.ph.pcsolottowatcher.databinding.DLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.d.DHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.results.d.DHistoryViewHolder;
import com.ph.pcsolottowatcher.recyclerview.holder.results.d.DLatestViewHolder;

public class DHistoryAdapter extends BaseListAdapter<DHistoryModel> {
  private static final int SIX_DIGITS_LATEST_ITEM = 0;
  private static final int SIX_DIGITS_HISTORY_ITEM = 1;

  public DHistoryAdapter(DiffUtil.ItemCallback<DHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SIX_DIGITS_LATEST_ITEM:
        return new DLatestViewHolder(
            DLatestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

      case SIX_DIGITS_HISTORY_ITEM:
        return new DHistoryViewHolder(
            DHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int viewType = getItemViewType(position);

    switch (viewType) {
      case SIX_DIGITS_LATEST_ITEM:
        DLatestViewHolder viewHolder0 = (DLatestViewHolder) holder;
        DHistoryModel model0 = getItem(position);

        viewHolder0.bind(model0);
        break;

      case SIX_DIGITS_HISTORY_ITEM:
        DHistoryViewHolder viewHolder1 = (DHistoryViewHolder) holder;
        DHistoryModel model1 = getItem(position);

        viewHolder1.bind(model1);
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) return SIX_DIGITS_LATEST_ITEM;
    else return SIX_DIGITS_HISTORY_ITEM;
  }
}
