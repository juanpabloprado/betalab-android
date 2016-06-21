package com.betalab.android.mainscreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
public class MainScreenMapFragment extends Fragment
    implements OnMapReadyCallback, View.OnClickListener {
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

    view.findViewById(R.id.fab_mainscreen_map_add).setOnClickListener(this);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    if (!(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(getContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      mMap.setMyLocationEnabled(true);
    } else {
      String[] permiss = new String[2];
      permiss[0] = Manifest.permission.ACCESS_FINE_LOCATION;
      permiss[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
      requestPermissions(permiss, 1234);
    }
    // Add a marker in Sydney and move the camera

    loadGooglePins(googleMap);

    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      @Override public void onMapClick(LatLng latLng) {
        //getView().findViewById(ddf)
      }
    });

    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
      @Override public boolean onMarkerClick(Marker marker) {
        if (incidenceMap != null && incidenceMap.containsKey(marker)) {
          Incidence incidence = incidenceMap.get(marker);
          // TODO: 6/21/16 Show things and stuff
          getView().findViewById(R.id.ll_main_map).setVisibility(View.VISIBLE);
          ((TextView) getView().findViewById(R.id.tv_main_map_title)).setText(incidence.title);
          ((TextView) getView().findViewById(R.id.tv_main_map_description)).setText(
              incidence.description);
        }
        return false;
      }
    });

    /*LatLng sydney = new LatLng(-34, 151);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    Log.d(TAG, String.format(
        "onRequestPermissionsResult() called with: requestCode = [%d], permissions = [%s], grantResults = [%s]",
        requestCode, permissions, grantResults));
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case 1234:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
            || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
          if (ActivityCompat.checkSelfPermission(getContext(),
              Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
              && ActivityCompat.checkSelfPermission(getContext(),
              Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
          }
          mMap.setMyLocationEnabled(true);
        }
    }
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

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fab_mainscreen_map_add:
        Log.d(TAG, "onClick() called with: " + "v = [" + String.valueOf(v.getId()) + "]");

        break;
    }
  }
}
