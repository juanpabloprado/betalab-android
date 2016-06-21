package com.betalab.android.util;

import android.app.Activity;
import android.content.Intent;
import com.betalab.android.mainscreen.MainScreenActivity;

public abstract class NavigationUtil {

  public static void goToMainScreenActivity(Activity activity) {
    Intent intent = new Intent(activity, MainScreenActivity.class);
    activity.startActivity(intent);
  }
}