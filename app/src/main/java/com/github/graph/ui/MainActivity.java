package com.github.graph.ui;

import android.os.Bundle;
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

        getSupportFragmentManager().beginTransaction().add(R.id.frame, barFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
