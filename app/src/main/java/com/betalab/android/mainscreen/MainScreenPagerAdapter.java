package com.betalab.android.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import com.betalab.android.R;
import com.betalab.android.pojos.Incidence;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class MainScreenPagerAdapter extends FragmentStatePagerAdapter
    implements IncidencesContainer {
  private static final String TAG = "MainScreenPagerAdapter";

  ArrayList<Incidence> incidences = new ArrayList<>();
  private Fragment[] fragments = new Fragment[3];

  private DatabaseReference mDatabaseReference;

  private ChildEventListener childEventListener = new ChildEventListener() {
    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      Log.d(TAG, "onChildAdded() called with: "
          + "dataSnapshot = ["
          + dataSnapshot
          + "], s = ["
          + s
          + "]");

      Incidence incidence = dataSnapshot.getValue(Incidence.class);
      incidences.add(incidence);

      ((MainScreenMapFragment) fragments[0]).onDataChange();

      /*if (mMap != null) {
        loadGooglePins(mMap);
      }*/

      //Log.d(TAG, "onChildAdded: " + incidence.getHistory().size());
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(DatabaseError databaseError) {

    }
  };

  public MainScreenPagerAdapter(FragmentManager fm) {
    super(fm);

    fragments[0] = MainScreenMapFragment.newInstance(this);
    //fragments[0].setRetainInstance(true);
    fragments[1] = new MainScreenListFragment();
    fragments[2] = new MainScreenProfileFragment();
    //fragments[2].setRetainInstance(true);

    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    mDatabaseReference.addChildEventListener(childEventListener);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return fragments[0];
      case 1:
        return fragments[1];
      case 2:
        return fragments[2];
    }
    return null;
  }

  @Override public int getCount() {
    return 3;
  }

  public int getIcon(int position) {
    switch (position) {
      case 0:
        return R.drawable.ic_tab_map;
      case 1:
        return R.drawable.ic_tab_feed;
    }

    return R.drawable.ic_tab_user;
  }

  @Override public ArrayList<Incidence> bringTheIncidences() {
    return incidences;
  }
}
