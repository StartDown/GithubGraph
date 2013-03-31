package com.github.graph.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.graph.R;
import com.github.graph.core.loader.ContributorsLoader;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.adapters.SingleTypeAdapter;
import com.github.graph.util.AvatarLoader;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import java.util.List;

public class BarChartFragment extends ItemListFragment<Contributor> {

    public static final String FRAGMENT_TAG = "fragment_bar_chart";

    private FrameLayout graphLayout;

    private Repository stubRepo;

    private AvatarLoader avatars;

    public static BarChartFragment newInstance() {
        return new BarChartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final User stubOwner = new User();
        stubOwner.setLogin("startdown");
        stubRepo = new Repository();
        stubRepo.setOwner(stubOwner);
        stubRepo.setName("GithubGraph");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_fragment, container, false);
        graphLayout = (FrameLayout) view.findViewById(R.id.graph);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_contributors);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_cotributors_load;
    }

    @Override
    protected SingleTypeAdapter<Contributor> createAdapter(List<Contributor> items) {
        avatars = new AvatarLoader(getActivity());
        return new ContributorAdapter(getActivity(), items, avatars);
    }

    @Override
    public Loader<List<Contributor>> onCreateLoader(int id, Bundle args) {
        return new ContributorsLoader(getActivity(), items, stubRepo);
    }

    @Override
    public void onLoadFinished(Loader<List<Contributor>> loader, List<Contributor> items) {
        super.onLoadFinished(loader, items);

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setXLabels(0);

        renderer.setShowLegend(false);
        renderer.setBarSpacing(0.2);
        renderer.setAntialiasing(true);
        renderer.setYLabels(0);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("contributors", 0);
        int max = 0;
        for(int i=0;i < items.size(); i++){
            int contr = items.get(i).getContributions();
            series.add(i, contr);
            renderer.addXTextLabel(i, items.get(i).getLogin());
            renderer.addYTextLabel(contr, ""+ contr);
            max = Math.max(max, contr);
        }
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(max);
        renderer.setXAxisMin(-0.5);
        renderer.setXAxisMax(items.size()-0.5);
        dataset.addSeries(series);

        XYSeriesRenderer xy = new XYSeriesRenderer();
        xy.setColor(0xFF0000FF);
        renderer.addSeriesRenderer(xy);

        GraphicalView q = ChartFactory.getBarChartView(getActivity(), dataset, renderer, BarChart.Type.DEFAULT);
        graphLayout.removeAllViews();
        graphLayout.addView(q);

        //graph.setBackground(new BarGraphDrawable(items));
    }
}
