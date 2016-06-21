package com.betalab.android.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.betalab.android.R;

public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

  private Fragment[] fragments = new Fragment[3];

  public MainScreenPagerAdapter(FragmentManager fm) {
    super(fm);

    fragments[0] = new MainScreenMapFragment();
    //fragments[0].setRetainInstance(true);
    fragments[1] = new MainScreenListFragment();
    fragments[2] = new MainScreenProfileFragment();
    //fragments[2].setRetainInstance(true);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return fragments[0];
      case 1:
        return fragments[1];
      case 2:
        return fragments[2];
    }
    return null;
  }

  @Override public int getCount() {
    return 3;
  }

  public int getIcon(int position) {
    switch (position) {
      case 0:
        return R.drawable.ic_tab_map;
      case 1:
        return R.drawable.ic_tab_feed;
    }

    return R.drawable.ic_tab_user;
  }
}
