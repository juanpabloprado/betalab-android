package com.betalab.android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import timber.log.Timber;

public class BetalabApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new ErrorTree());
    }
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  private static class ErrorTree extends Timber.Tree {
    private static final String TAG = "Betalab";

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.ERROR) {
        Log.e(TAG, message, t);
      }
    }
  }
}
