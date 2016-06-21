package com.betalab.android.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.betalab.android.R;

public class MainScreenActivity extends AppCompatActivity {

  MainScreenPagerAdapter mainScreenPagerAdapter;

  ViewPager mainScreenViewPager;
  TabLayout mainScreenTabLayout;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main_screen);

    mainScreenTabLayout = (TabLayout) findViewById(R.id.tl_mainscreen);
    mainScreenViewPager = (ViewPager) findViewById(R.id.vp_mainscreen);

    setupViewPager();
  }

  private void setupViewPager() {
    mainScreenPagerAdapter = new MainScreenPagerAdapter(this, getSupportFragmentManager());
    mainScreenViewPager.setAdapter(mainScreenPagerAdapter);
    mainScreenTabLayout.setupWithViewPager(mainScreenViewPager);
    for (int i = 0; i < mainScreenTabLayout.getTabCount(); i++) {
      mainScreenTabLayout.getTabAt(i).setIcon(mainScreenPagerAdapter.getIcon(i));
    }
  }
}
