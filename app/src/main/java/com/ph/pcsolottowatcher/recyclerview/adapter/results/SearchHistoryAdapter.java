package com.ph.pcsolottowatcher.recyclerview.adapter.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.databinding.SearchHistoryItemBinding;
import com.ph.pcsolottowatcher.recyclerview.RootAdapter;
import com.ph.pcsolottowatcher.recyclerview.RootListAdapter;
import com.ph.pcsolottowatcher.recyclerview.holder.results.search.SearchHistoryViewHolder;

public class SearchHistoryAdapter extends RootListAdapter<String> {
  private RootAdapter.SearchHistoryItemClickListener listener;

  public SearchHistoryAdapter(
      DiffUtil.ItemCallback<String> diff, RootAdapter.SearchHistoryItemClickListener listener) {
    super(diff);
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SearchHistoryViewHolder(
        SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    String item = getItem(position);

    ((SearchHistoryViewHolder) holder).bind(listener, item);
  }
}
