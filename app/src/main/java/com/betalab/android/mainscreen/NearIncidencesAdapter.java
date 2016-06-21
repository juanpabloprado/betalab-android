package com.betalab.android.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.betalab.android.R;
import com.betalab.android.pojos.Incidence;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Shekomaru on 6/21/16.
 */
public class NearIncidencesAdapter extends RecyclerView.Adapter<NearIncidencesAdapter.NearHolder> {

  ArrayList<Incidence> incidences;

  public NearIncidencesAdapter(ArrayList<Incidence> incidences) {
    this.incidences = incidences;
  }

  @Override public NearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NearHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_nearincidences, parent, false));
  }

  public void changeData(ArrayList<Incidence> incidences) {
    this.incidences = incidences;
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(NearHolder holder, int position) {
    holder.title.setText(incidences.get(position).title);
    holder.description.setText(incidences.get(position).description);
    Picasso.with(holder.title.getContext())
        .load(incidences.get(position).getPicture())
        .into(holder.preview);
  }

  @Override public int getItemCount() {
    return incidences != null ? incidences.size() : 0;
  }

  public class NearHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    ImageView preview;

    public NearHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView.findViewById(R.id.tv_main_list_title);
      description = (TextView) itemView.findViewById(R.id.tv_main_list_description);
      preview = (ImageView) itemView.findViewById(R.id.iv_main_list_image);
    }
  }
}
