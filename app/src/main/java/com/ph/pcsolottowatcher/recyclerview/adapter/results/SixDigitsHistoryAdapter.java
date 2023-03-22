package com.ph.pcsolottowatcher.recyclerview.adapter.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.SixDigitsHistoryItemBinding;
import com.ph.pcsolottowatcher.databinding.SixDigitsLatestItemBinding;
import com.ph.pcsolottowatcher.pojos.sixdigits.SixDigitsHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.holder.results.sixdigits.SixDigitsHistoryViewHolder;
import com.ph.pcsolottowatcher.recyclerview.holder.results.sixdigits.SixDigitsLatestViewHolder;

public class SixDigitsHistoryAdapter extends BaseListAdapter<SixDigitsHistoryModel> {
  private static final int SIX_DIGITS_LATEST_ITEM = 0;
  private static final int SIX_DIGITS_HISTORY_ITEM = 1;

  public SixDigitsHistoryAdapter(DiffUtil.ItemCallback<SixDigitsHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SIX_DIGITS_LATEST_ITEM:
        return new SixDigitsLatestViewHolder(
            SixDigitsLatestItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      case SIX_DIGITS_HISTORY_ITEM:
        return new SixDigitsHistoryViewHolder(
            SixDigitsHistoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int viewType = getItemViewType(position);

    switch (viewType) {
      case SIX_DIGITS_LATEST_ITEM:
        SixDigitsLatestViewHolder viewHolder0 = (SixDigitsLatestViewHolder) holder;
        SixDigitsHistoryModel model0 = getItem(position);

        viewHolder0.bind(model0);
        break;

      case SIX_DIGITS_HISTORY_ITEM:
        SixDigitsHistoryViewHolder viewHolder1 = (SixDigitsHistoryViewHolder) holder;
        SixDigitsHistoryModel model1 = getItem(position);

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
