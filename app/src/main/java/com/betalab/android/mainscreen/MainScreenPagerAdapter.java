package com.betalab.android.mainscreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.betalab.android.R;

public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

  protected Context mContext;

  public MainScreenPagerAdapter(Context context, FragmentManager fm) {
    super(fm);
    mContext = context;
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
