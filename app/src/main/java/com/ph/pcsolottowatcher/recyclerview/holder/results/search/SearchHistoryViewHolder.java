package com.ph.pcsolottowatcher.recyclerview.holder.results.search;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.databinding.SearchHistoryItemBinding;
import com.ph.pcsolottowatcher.recyclerview.RootAdapter;

public class SearchHistoryViewHolder extends RecyclerView.ViewHolder {
  private TextView search;
  private ImageView iv_put;

  public SearchHistoryViewHolder(SearchHistoryItemBinding binding) {
    super(binding.getRoot());
    this.search = binding.tvSearchHistory;
    this.iv_put = binding.ivPut;
  }

  public void bind(RootAdapter.SearchHistoryItemClickListener listener, String model) {
    search.setText(model);

    itemView.setOnClickListener(
        v -> {
          listener.onSearchClick(model);
        });

    iv_put.setOnClickListener(
        v -> {
          listener.onPutTextToSearch(model);
        });
  }
}
