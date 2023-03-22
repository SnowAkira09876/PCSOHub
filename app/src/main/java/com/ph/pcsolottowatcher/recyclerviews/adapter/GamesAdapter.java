package com.ph.pcsolottowatcher.recyclerviews.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ph.pcsolottowatcher.common.recyclerview.BaseListAdapter;
import com.ph.pcsolottowatcher.databinding.GamesItemBinding;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;
import com.ph.pcsolottowatcher.recyclerviews.holder.GamesViewHolder;

public class GamesAdapter extends BaseListAdapter<LottoGameBaseModel> {
  private BaseListAdapter.ItemClickListener listener;
  public int lastChecked = -1;
  private boolean toClearCheck = false;

  public GamesAdapter(
      DiffUtil.ItemCallback<LottoGameBaseModel> diff, BaseListAdapter.ItemClickListener listener) {
    super(diff);
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new GamesViewHolder(
        GamesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    GamesViewHolder viewHolder = (GamesViewHolder) holder;
    LottoGameBaseModel model = getItem(position);
    viewHolder.bind(this, listener, model, position);

    if (toClearCheck) viewHolder.clearCheck();
  }

  public void clearCheck() {
    this.toClearCheck = true;
  }
}
