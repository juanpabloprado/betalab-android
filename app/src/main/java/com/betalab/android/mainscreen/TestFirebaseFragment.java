package com.betalab.android.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.betalab.android.R;
import com.betalab.android.pojos.Incidence;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shekomaru on 6/20/16.
 *
 * FIXME: Delete this fragment, because this is only to test the firebase database functionality
 */
public class TestFirebaseFragment extends Fragment {
  private static final String TAG = "TestFirebaseFragment";

  private DatabaseReference mDatabaseReference;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_testfirebase, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    mDatabaseReference.addChildEventListener(new ChildEventListener() {
      @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, "onChildAdded() called with: "
            + "dataSnapshot = ["
            + dataSnapshot
            + "], s = ["
            + s
            + "]");

        Incidence incidence = dataSnapshot.getValue(Incidence.class);

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
    });
  }

  private JSONArray getJSONArray(Object[] objects) {
    JSONArray jsonArray = new JSONArray();

    return jsonArray;
  }

  private JSONArray getJSONArray(String string) {
    try {
      JSONArray jsonArray = new JSONArray(string);
      Log.d(TAG, "getJSONArray: :)");
      return new JSONArray(string);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    Log.d(TAG, "getJSONArray: :(");
    return new JSONArray();
  }

  private JSONObject getJSONObject(Object object) {
    JSONObject jsonObject = new JSONObject();
    /*for(Object object2: object){

    }*/
    try {
      jsonObject = new JSONObject(object.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return jsonObject;
  }
}
