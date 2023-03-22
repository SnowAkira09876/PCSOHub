package com.ph.pcsolottowatcher.recyclerviews.holder;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.GamesItemBinding;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.recyclerviews.adapter.GamesAdapter;

public class GamesViewHolder extends RecyclerView.ViewHolder {
  public TextView tvTitle;
  private MaterialCardView card;

  public GamesViewHolder(GamesItemBinding binding) {
    super(binding.getRoot());
    this.tvTitle = binding.tv;
    this.card = binding.getRoot();
  }

  public void bind(
      GamesAdapter adapter,
      BaseListAdapter.ItemClickListener listener,
      LottoGameBaseModel model,
      int position) {

    tvTitle.setText(model.getName());
    itemView.setOnClickListener(
        v -> {
          if (position != adapter.lastChecked) {
            adapter.notifyItemChanged(adapter.lastChecked);
            adapter.lastChecked = position;
          }

          card.setChecked(true);
          listener.onItemClick(model);
        });

    if (position != adapter.lastChecked) card.setChecked(false);
    else card.setChecked(true);
  }

  public void clearCheck() {
    card.setChecked(false);
  }
}
