package com.betalab.android.base.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.betalab.android.R;
import com.betalab.android.loginscreen.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

  protected FirebaseAuth mAuth;

  private ProgressDialog mProgressDialog;

  private FirebaseAuth.AuthStateListener mAuthListener;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Timber.d("onAuthStateChanged:signed_in: %s", user.getUid());
        } else {
          // User is signed out
          Timber.d("onAuthStateChanged:signed_out");
        }
      }
    };
  }

  @Override
  public void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    // If the user is offline, let them know they are not connected
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    if ((ni == null) || (!ni.isConnected())) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.device_offline_message), Toast.LENGTH_LONG).show();
    }
  }

  protected void navigateToLogin() {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void showProgressDialog() {
    mProgressDialog = ProgressDialog.show(this, null,
        getString(R.string.progress_dialog_loading), true, false);
  }

  public void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
        mProgressDialog.dismiss();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    hideProgressDialog();
  }
}
