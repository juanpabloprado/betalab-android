package com.betalab.android.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.betalab.android.R;
import com.betalab.android.pojos.Incidence;
import java.util.ArrayList;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class NearIncidencesAdapter extends RecyclerView.Adapter<NearIncidencesAdapter.NearHolder> {

  ArrayList<Incidence> incidences;

  /**
   * Seriously, kill this constructor when going to production
   */
  @Deprecated public NearIncidencesAdapter(int incidencesCount) {
    incidences = new ArrayList<>();

    for (int i = 0; i < incidencesCount; i++) {
      Incidence incidence = new Incidence();
      incidence.title = String.format("Incidence number %d", i);
      incidence.description =
          "This is an incidence that could be good to be fixed, so fix it, goverment!";
      incidences.add(incidence);
    }
  }

  @Override public NearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NearHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_nearincidences, parent, false));
  }

  @Override public void onBindViewHolder(NearHolder holder, int position) {
    holder.title.setText(incidences.get(position).title);
    holder.description.setText(incidences.get(position).description);
  }

  @Override public int getItemCount() {
    return incidences != null ? incidences.size() : 0;
  }

  public class NearHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;

    public NearHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView.findViewById(R.id.tv_main_list_title);
      description = (TextView) itemView.findViewById(R.id.tv_main_list_description);
    }
  }
}
