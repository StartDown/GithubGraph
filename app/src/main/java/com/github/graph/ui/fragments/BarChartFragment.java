package com.github.graph.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.ListView;
import com.github.graph.R;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.widget.BarGraphDrawable;
import org.eclipse.egit.github.core.Contributor;=======
import com.github.graph.R;
import com.github.graph.core.loader.ContributorsLoader;
import com.github.graph.ui.widget.BarGraphDrawable;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yarik
 * Date: 3/30/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarChartFragment extends ItemListFragment<Contributor> {

    private ImageView graph;

    private Repository stubRepo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final User stubOwner = new User();
        stubOwner.setLogin("github");
        stubRepo = new Repository();
        stubRepo.setOwner(stubOwner);
        stubRepo.setName("android");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_fragment, container, false);
        graph = (ImageView) view.findViewById(R.id.graph);
        return view;
    }

    public void setContributors(List<Contributor> contributors) {
        ContributorAdapter adapter = new ContributorAdapter(this.getActivity(), contributors);
        list.setAdapter(adapter);


        graph.setBackground(new BarGraphDrawable(contributors));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setEmptyText(R.string.no_contributors);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_cotributors_load;
    }

    @Override
    protected SingleTypeAdapter<Contributor> createAdapter(List<Contributor> items) {
        return null;
    }

    @Override
    public Loader<List<Contributor>> onCreateLoader(int id, Bundle args) {
        return new ContributorsLoader(getActivity(), items, stubRepo);
    }

    public void setData(int[] data) {
        long[][] mData = new long[data.length][1];
        int[][] colors = new int[data.length][1];
        for (int i = 0; i < data.length; i++) {
            mData[i][0] = data[i];
            colors[i][0] = 0xFF00FF00;
        }
        graph.setBackground(new BarGraphDrawable(mData, colors));
    }
}
