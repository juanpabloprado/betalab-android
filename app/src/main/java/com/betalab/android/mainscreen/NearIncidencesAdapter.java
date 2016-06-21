package com.betalab.android.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class NearIncidencesAdapter extends RecyclerView.Adapter<NearIncidencesAdapter.NearHolder> {

  /**
   * Seriously, kill this constructor when going to production
   */
  @Deprecated public NearIncidencesAdapter(int incidencesCount) {

  }

  @Override public NearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(NearHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  public class NearHolder extends RecyclerView.ViewHolder {

    public NearHolder(View itemView) {
      super(itemView);
    }
  }
}
