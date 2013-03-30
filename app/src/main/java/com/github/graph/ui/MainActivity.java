package com.github.graph.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import com.github.graph.R;
import com.github.graph.ui.fragments.BarChartFragment;
import roboguice.activity.RoboFragmentActivity;

public class MainActivity extends RoboFragmentActivity {

    BarChartFragment barFragment = new BarChartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(BarChartFragment.FRAGMENT_TAG) == null) {
            Fragment fragment = BarChartFragment.newInstance();
            fm.beginTransaction().add(R.id.frame, fragment, BarChartFragment.FRAGMENT_TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
