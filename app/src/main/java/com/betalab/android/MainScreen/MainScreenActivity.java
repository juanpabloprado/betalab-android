package com.betalab.android.MainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.betalab.android.R;

/**
 * Created by Shekomaru on 6/20/16.
 */
public class MainScreenActivity extends AppCompatActivity {

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
    mainScreenViewPager.setAdapter(new MainScreenPagerAdapter(getSupportFragmentManager()));
    mainScreenTabLayout.setupWithViewPager(mainScreenViewPager);
  }
}
