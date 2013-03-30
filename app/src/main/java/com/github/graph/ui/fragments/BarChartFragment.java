package com.github.graph.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.graph.R;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.adapters.SingleTypeAdapter;
import com.github.graph.ui.widget.BarGraphDrawable;
import org.eclipse.egit.github.core.Contributor;
import com.github.graph.core.loader.ContributorsLoader;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import java.util.List;

public class BarChartFragment extends ItemListFragment<Contributor> {

    public static final String FRAGMENT_TAG = "fragment_bar_chart";

    private ImageView graph;

    private Repository stubRepo;

    public static BarChartFragment newInstance() {
        return new BarChartFragment();
    }

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
        return new ContributorAdapter(getActivity(), items);
    }

    @Override
    public Loader<List<Contributor>> onCreateLoader(int id, Bundle args) {
        return new ContributorsLoader(getActivity(), items, stubRepo);
    }

    @Override
    public void onLoadFinished(Loader<List<Contributor>> loader, List<Contributor> items) {
        super.onLoadFinished(loader, items);
        graph.setBackground(new BarGraphDrawable(items));
    }
}
