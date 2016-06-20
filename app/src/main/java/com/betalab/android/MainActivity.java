package com.betalab.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String TAG = "MainActivity";

  FirebaseDatabase database;
  DatabaseReference myRef;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //firebase
    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("message");

    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {

        String value = dataSnapshot.getValue(String.class);
        Log.d(TAG, "Value is: " + value);
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });

    findViewById(R.id.bt1).setOnClickListener(this);
    findViewById(R.id.bt2).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt1:

        myRef.setValue("Hello, World!");
        Toast.makeText(MainActivity.this, "Data written!", Toast.LENGTH_SHORT).show();
        break;
      case R.id.bt2:

        break;
    }
  }
}
