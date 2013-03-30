package com.github.graph.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.github.graph.R;
import com.github.graph.ui.fragments.BarChartFragment;
import org.eclipse.egit.github.core.Contributor;

import java.util.ArrayList;
import java.util.List;

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

        List<Contributor> cs = new ArrayList<Contributor>();
        Contributor cb = new Contributor();
        cb.setLogin("Foo");
        cb.setContributions(30);
        cs.add(cb);

        cb = new Contributor();
        cb.setLogin("Bar");
        cb.setContributions(50);
        cs.add(cb);

        cb = new Contributor();
        cb.setLogin("Jesus");
        cb.setContributions(70);
        cs.add(cb);

        cb = new Contributor();
        cb.setLogin("Allah");
        cb.setContributions(80);
        cs.add(cb);

        barFragment.setContributors(cs);
    }
}
