package com.ph.pcsolottowatcher.recyclerview.adapter.allresults;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.databinding.AllHistoryItemBinding;
import com.ph.pcsolottowatcher.pojos.results.local.LocalHistoryModel;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;
import com.ph.pcsolottowatcher.recyclerview.holder.allresults.AllHistoryViewHolder;

public class AllResultsAdapter extends RootListAdapter<LocalHistoryModel> {

  public AllResultsAdapter(DiffUtil.ItemCallback<LocalHistoryModel> diff) {
    super(diff);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new AllHistoryViewHolder(
        AllHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    AllHistoryViewHolder viewHolder = (AllHistoryViewHolder) holder;
    LocalHistoryModel model = getItem(position);

    viewHolder.bind(model);
  }
}
