package com.betalab.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  FirebaseDatabase database;
  DatabaseReference myRef;

  @BindView(R.id.bt1) Button firstButton;
  @BindView(R.id.bt2) Button secondButton;
  @BindView(R.id.et1) EditText firstEditText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_mainscreen_user);
    ButterKnife.bind(this);

    //firebase
    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("message");

    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {

        String value = dataSnapshot.getValue(String.class);
        Timber.i("Value is: " + value);
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Timber.i("onCancelled() called with: " + "databaseError = [" + databaseError + "]");
      }
    });

    firstButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String string = firstEditText.getText().toString();
        myRef.setValue(string);
        Toast.makeText(MainActivity.this, "Data written!", Toast.LENGTH_SHORT).show();
      }
    });

    secondButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
      }
    });

  }

}
