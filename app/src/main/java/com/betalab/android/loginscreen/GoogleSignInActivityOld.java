/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betalab.android.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.betalab.android.R;
import com.betalab.android.base.activity.BaseActivity;
import com.betalab.android.util.NavigationUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import timber.log.Timber;

public class GoogleSignInActivityOld extends BaseActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  private static final int RC_SIGN_IN = 9001;
  @BindView(R.id.status) TextView mStatusTextView;
  @BindView(R.id.detail) TextView mDetailTextView;
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private GoogleApiClient mGoogleApiClient;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_google);

    buildGoogleApiClient();

    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Timber.d("onAuthStateChanged:signed_in: %s", user.getUid());
        } else {
          // User is signed out
          Timber.d("onAuthStateChanged:signed_out");
        }
        updateUI(user);
      }
    };
  }

  private void buildGoogleApiClient() {
    GoogleSignInOptions gso = configureGoogleSignIn();

    mGoogleApiClient =
        new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
  }

  @NonNull private GoogleSignInOptions configureGoogleSignIn() {
    return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
        getString(R.string.default_web_client_id)).requestEmail().build();
  }

  @Override public void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  @Override public void onStop() {
    super.onStop();
    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      } else {
        // Google Sign In failed, update UI appropriately
        updateUI(null);
      }
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Timber.d("firebaseAuthWithGoogle: %s", acct.getId());
    showProgressDialog();

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            Timber.d("signInWithCredential:onComplete: %s", task.isSuccessful());

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
              Timber.w(task.getException(), "signInWithCredential");
              Toast.makeText(GoogleSignInActivityOld.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            } else {
              NavigationUtil.goToMainScreenActivity(GoogleSignInActivityOld.this);
              finish();
            }
            hideProgressDialog();
          }
        });
  }

  @OnClick(R.id.sign_in_button) void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @OnClick(R.id.sign_out_button) void signOut() {
    // Firebase sign out
    mAuth.signOut();

    // Google sign out
    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
      @Override public void onResult(@NonNull Status status) {
        updateUI(null);
      }
    });
  }

  @OnClick(R.id.disconnect_button) void revokeAccess() {
    // Firebase sign out
    mAuth.signOut();

    // Google revoke access
    Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
        .setResultCallback(new ResultCallback<Status>() {
          @Override public void onResult(@NonNull Status status) {
            updateUI(null);
          }
        });
  }

  private void updateUI(FirebaseUser user) {
    hideProgressDialog();
    if (user != null) {
      mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
      mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

      findViewById(R.id.sign_in_button).setVisibility(View.GONE);
      findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
    } else {
      mStatusTextView.setText(R.string.signed_out);
      mDetailTextView.setText(null);

      findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
      findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
    }
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    // An unresolvable error has occurred and Google APIs (including Sign-In) will not
    // be available.
    Timber.d("onConnectionFailed: %s", connectionResult);
    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }
}
