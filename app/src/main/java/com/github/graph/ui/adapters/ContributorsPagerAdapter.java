package com.github.graph.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.github.graph.ui.fragments.BarChartFragment;
import com.github.graph.ui.fragments.ContributorsFragment;
import com.github.graph.ui.fragments.PunchcardFragment;

public class ContributorsPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_BAR_CHART = 0;
    public static final int TAB_CONTRIBUTORS = 1;
    public static final int TAB_PUNCHCARD = 2;

    public ContributorsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_BAR_CHART:
                return BarChartFragment.newInstance();
            case TAB_CONTRIBUTORS:
                return ContributorsFragment.newInstance();

            case TAB_PUNCHCARD:
                return PunchcardFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
