package com.betalab.android.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.betalab.android.R;
import com.betalab.android.pojos.GeoData;
import com.betalab.android.pojos.Incidence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Shekomaru on 6/20/16.
 */
public class MainScreenMapFragment extends Fragment implements OnMapReadyCallback {
  private static final String TAG = "MainScreenMapFragment";

  private GoogleMap mMap;

  private Map<Marker, Incidence> incidenceMap;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_mainscreen_map, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    incidenceMap = new HashMap<>();

    SupportMapFragment mapFragment =
        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // Add a marker in Sydney and move the camera

    loadGooglePins(googleMap);

    /*LatLng sydney = new LatLng(-34, 151);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
  }

  private void loadGooglePins(GoogleMap googleMap) {
    boolean locationsWereAdded = false;

    ArrayList<Incidence> incidences = getFakeIncidences(5);

    final LatLngBounds.Builder builder = LatLngBounds.builder();

    for (Incidence incidence : incidences) {
      locationsWereAdded = true;

      LatLng position = new LatLng(Double.valueOf(incidence.gmap.latitud),
          Double.valueOf(incidence.gmap.longitud));

      Log.d(TAG, "loadGooglePins: Added thing with coordinates: "
          + position.latitude
          + ", "
          + position.longitude);

      Marker marker =
          googleMap.addMarker(new MarkerOptions().position(position).title(incidence.title));

      incidenceMap.put(marker, incidence);
      builder.include(position);
    }

    if (locationsWereAdded) {

      mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
        @Override public void onMapLoaded() {
          mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 25), null);
        }
      });
    }
  }

  @Nullable private ArrayList<Incidence> getFakeIncidences(int incidencesQuantity) {
    ArrayList<Incidence> incidences = new ArrayList<>();
    for (int i = 0; i < incidencesQuantity; i++) {
      Incidence incidence = new Incidence();
      incidence.gmap = new GeoData();
      incidence.gmap.latitud = String.valueOf(21.9005001 + (new Random()).nextDouble() * 0.05);
      incidence.gmap.longitud = String.valueOf(-102.3168918 + (new Random()).nextDouble() * 0.07);
      incidence.title = String.format("Incidence %d", i);
      incidence.description = "Esta incidencia deberia de ser solucionada";

      incidences.add(incidence);
    }
    return incidences;
  }
}
