package com.betalab.android.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.betalab.android.R;
import com.betalab.android.base.activity.BaseActivity;
import com.betalab.android.util.NavigationUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class LoginActivity extends BaseActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  private static final int RC_SIGN_IN = 9001;

  public static final List<String> mPermissions = new ArrayList<String>() {{
    add("public_profile");
    add("email");
  }};

  @BindView(R.id.button_facebook_login) LoginButton facebookLoginButton;

  private CallbackManager mCallbackManager;
  private GoogleApiClient mGoogleApiClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    buildGoogleApiClient();

    // Initialize Facebook Login button
    setUpFacebookLogin();
  }

  @OnClick(R.id.sign_in_button) void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
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

  private void setUpFacebookLogin() {
    mCallbackManager = CallbackManager.Factory.create();
    facebookLoginButton.setReadPermissions(mPermissions);
    facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        Timber.d("facebook:onSuccess: %s", loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
      }

      @Override
      public void onCancel() {
        Timber.d("facebook:onCancel");
      }

      @Override
      public void onError(FacebookException error) {
        Timber.d(error, "facebook:onError");
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //mCallbackManager.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      } else {
        // Google Sign In failed, update UI appropriately
        Timber.i("Google Sign In failed, update UI appropriately");
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
              Toast.makeText(LoginActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            } else {
              NavigationUtil.goToMainScreenActivity(LoginActivity.this);
              finish();
            }
            hideProgressDialog();
          }
        });
  }

  private void handleFacebookAccessToken(AccessToken token) {
    Timber.d("handleFacebookAccessToken: %s", token);
    showProgressDialog();

    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            Timber.d("signInWithCredential:onComplete: %s", task.isSuccessful());

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
              Timber.w(task.getException(), "signInWithCredential");
              Toast.makeText(LoginActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            }

            hideProgressDialog();
          }
        });
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    // An unresolvable error has occurred and Google APIs (including Sign-In) will not
    // be available.
    Timber.d("onConnectionFailed: %s", connectionResult);
    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }
}
