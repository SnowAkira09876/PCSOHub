package com.ph.pcsolottowatcher.recyclerview;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.ph.pcsolottowatcher.pojos.BaseFirebaseModel;

public abstract class RootFirebaseAdapter<T extends BaseFirebaseModel>
    extends FirebaseRecyclerAdapter<T, RecyclerView.ViewHolder> {

  private Class<T> modelClass;

  public RootFirebaseAdapter(Class<T> modelClass, Query query) {
    super(new FirebaseRecyclerOptions.Builder<T>().setQuery(query, modelClass).build());
    this.modelClass = modelClass;
  }

  public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

  protected abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position, T model);

  @SuppressLint("NotifyDataSetChanged")
  public void updateQuery(Query newQuery) {
    FirebaseRecyclerOptions<T> options =
        new FirebaseRecyclerOptions.Builder<T>().setQuery(newQuery, modelClass).build();
    updateOptions(options);
    notifyDataSetChanged();
  }
}
