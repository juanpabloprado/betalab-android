package com.betalab.android.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.betalab.android.R;
import com.betalab.android.pojos.Incidence;
import java.util.ArrayList;

/**
 * Created by Shekomaru on 6/20/16.
 */
public class MainScreenListFragment extends Fragment {

  private IncidencesContainer incidencesContainer;

  public static MainScreenListFragment newInstance(IncidencesContainer incidencesContainer) {
    Bundle args = new Bundle();
    MainScreenListFragment fragment = new MainScreenListFragment();
    fragment.setArguments(args);

    fragment.incidencesContainer = incidencesContainer;

    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_mainscreen_list, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView mainRecyclerView = (RecyclerView) view;

    mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mainRecyclerView.setAdapter(new NearIncidencesAdapter(incidencesContainer.bringTheIncidences()));
  }

  public void onDataChange() {
    // TODO: 6/21/16 Read the data and everything
    ((NearIncidencesAdapter) ((RecyclerView) getView()).getAdapter()).changeData(
        incidencesContainer.bringTheIncidences());
  }
}
