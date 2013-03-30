package com.github.graph.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.github.graph.R;
import com.github.graph.ui.fragments.BarChartFragment;

public class MainActivity extends Activity {

    BarChartFragment barFragment = new BarChartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.frame, barFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
        barFragment.setData(new int[]{5, 2, 6, 3, 6, 9, 3});
    }
}
