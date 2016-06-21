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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

/*    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange(father) called with: " + "dataSnapshot = [" + dataSnapshot + "]");

        Log.d(TAG, "onDataChange: Data father to string: " + getJSONArray(
            dataSnapshot.toString()).toString());

        //GenericTypeIndicator
        ArrayList<Incidence> incidences = (ArrayList<Incidence>) dataSnapshot.getValue();
        if (incidences != null) {
          Log.d(TAG, "onDataChange: " + incidences.toString());
          for (Incidence incidence : incidences) {
            Log.d(TAG, "onDataChange: " + incidence.toString());
          }
        } else {
          Log.d(TAG, "onDataChange: Could not convert that thing");
        }

        Object object = dataSnapshot.getValue();
        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();

        for (DataSnapshot temp : iterable) {
          Object object2 = temp.getValue();
          if (object2 instanceof Boolean) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isBoolean");
          } else if (object2 instanceof String) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isString");
          } else if (object2 instanceof Long) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isLong");
          } else if (object2 instanceof Double) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isDouble");
          } else if (object2 instanceof Map) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isMap" + ((Map) object2).keySet());
          } else if (object2 instanceof List) {
            Log.d(TAG, "onDataChange: " + temp.getKey() + "isList");
          }
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "onCancelled(father) called with: " + "databaseError = [" + databaseError + "]");
      }
    })*/;
    mDatabaseReference.child("betalabs-14933")
        .addListenerForSingleValueEvent(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG,
                "onDataChange(children) called with: " + "dataSnapshot = [" + dataSnapshot + "]");
          }

          @Override public void onCancelled(DatabaseError databaseError) {
            Log.d(TAG,
                "onCancelled(children) called with: " + "databaseError = [" + databaseError + "]");
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
