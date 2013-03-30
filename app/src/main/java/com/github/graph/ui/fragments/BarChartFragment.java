package com.github.graph.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Toast;
import com.github.graph.R;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.adapters.SingleTypeAdapter;
import com.github.graph.ui.widget.BarGraphDrawable;
import com.github.graph.util.AvatarLoader;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.eclipse.egit.github.core.Contributor;
import com.github.graph.core.loader.ContributorsLoader;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import java.util.List;

public class BarChartFragment extends ItemListFragment<Contributor> {

    public static final String FRAGMENT_TAG = "fragment_bar_chart";

    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;
    private GraphicalView mChartView;

    private LinearLayout graph;

    private Repository stubRepo;

    private AvatarLoader avatars;

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
        graph = (LinearLayout) view.findViewById(R.id.graph);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_contributors);

        initChart();
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
//        graph.setBackground(new BarGraphDrawable(items));
        updateChart();
    }

    private void initChart() {
        // set some properties on the main renderer
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setPointSize(5);

        if (mChartView == null) {
            mChartView = ChartFactory.getBarChartView(getActivity(), mDataset, mRenderer, BarChart.Type.STACKED);
             //getBarChartView(getActivity(), mDataset, mRenderer);

            // enable the chart click events
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            mChartView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // handle the click event on the chart
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(getActivity(), "No chart element", Toast.LENGTH_SHORT).show();
                    } else {
                        // display information of the clicked point
                        Toast.makeText(
                                getActivity(),
                                "Chart element in series index " + seriesSelection.getSeriesIndex()
                                        + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                                        + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                                        + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            graph.addView(mChartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
        } else {
            mChartView.repaint();
        }
    }

    private void updateChart() {
        String seriesTitle = "Series " + (mDataset.getSeriesCount() + 1);
        XYSeries series = new XYSeries(seriesTitle);
        mDataset.addSeries(series);
        mCurrentSeries = series;
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setDisplayChartValues(true);
        mCurrentRenderer = renderer;
        mChartView.repaint();

        int x = 0;
        for (Contributor contributor : items) {
            if (contributor.getContributions() > 3) {
                mCurrentSeries.add(x, contributor.getContributions());
                x += 10;
            }
        }

        mChartView.repaint();
    }
}
