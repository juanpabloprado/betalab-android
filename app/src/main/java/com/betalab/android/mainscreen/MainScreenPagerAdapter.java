package com.betalab.android.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Shekomaru on 6/20/16.
 */
public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {
  private static final String TAG = "MainScreenPagerAdapter";

  public MainScreenPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new MainScreenMapFragment();
      case 1:
        return new MainScreenListFragment();
      case 2:
        return new MainScreenProfileFragment();
    }
    return null;
  }

  @Override public int getCount() {
    return 3;
  }
}
