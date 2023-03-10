package com.ph.pcsolottowatcher.recyclerview;

import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ph.pcsolottowatcher.pojos.LottoGameBaseModel;

public abstract class RootListAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> {

  public RootListAdapter(DiffUtil.ItemCallback<T> diff) {
    super(diff);
  }

  public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

  public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

  public interface ItemClickListener {
    void onItemClick(LottoGameBaseModel model);
  }
}
