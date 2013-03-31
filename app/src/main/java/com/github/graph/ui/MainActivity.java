package com.github.graph.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import com.github.graph.R;
import com.github.graph.ui.adapters.ContributorsPagerAdapter;
import roboguice.activity.RoboFragmentActivity;

public class MainActivity extends RoboFragmentActivity
        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabsPager();

//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.findFragmentByTag(BarChartFragment.FRAGMENT_TAG) == null) {
//            Fragment fragment = BarChartFragment.newInstance();
//            fm.beginTransaction().replace(R.id.frame, fragment, BarChartFragment.FRAGMENT_TAG).commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPageSelected(int position) {
        getActionBar().setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void initTabsPager() {
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ContributorsPagerAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText("Barchart")
                .setTag(ContributorsPagerAdapter.TAB_BAR_CHART)
                .setTabListener(this));

        actionBar.addTab(actionBar.newTab()
                .setText("Contributors")
                .setTag(ContributorsPagerAdapter.TAB_CONTRIBUTORS)
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab()
            .setText("Punchcard")
            .setTag(ContributorsPagerAdapter.TAB_PUNCHCARD)
            .setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        Integer position = (Integer) tab.getTag();
        pager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
    }
}
