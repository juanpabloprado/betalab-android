package com.betalab.android.base.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.betalab.android.R;
import com.betalab.android.loginscreen.GoogleSignInActivity;

public class BaseActivity extends AppCompatActivity {

  private ProgressDialog mProgressDialog;

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
    Intent intent = new Intent(this, GoogleSignInActivity.class);
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
