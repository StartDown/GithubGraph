package com.github.graph.ui.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.graph.R;
import com.github.graph.util.AvatarLoader;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PunchcardFragment extends Fragment {

    public static final String FRAGMENT_TAG = "fragment_bar_chart";

    private FrameLayout graphLayout;

    private Repository stubRepo;


    public static PunchcardFragment newInstance() {
        return new PunchcardFragment();
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
        View view = inflater.inflate(R.layout.punchcard_fragment, container, false);
        graphLayout = (FrameLayout) view.findViewById(R.id.graph);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        setEmptyText(R.string.no_contributors);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        new AsyncTask<Void, Void, List<RepositoryCommit>>() {
            @Override
            protected List<RepositoryCommit> doInBackground(Void... params) {
                try {
                    GitHubClient client = new GitHubClient();
                    client.setCredentials("", "");
                    return new CommitService(client).getCommits(stubRepo);  //To change body of implemented methods use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<RepositoryCommit> repositoryCommits) {
                if (repositoryCommits != null) {
                    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

                    renderer.setShowLegend(false);
                    renderer.setBarSpacing(0.2);
                    renderer.setAntialiasing(true);
                    renderer.setShowGrid(true);

                    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

                    int[][] card = new int[7][24];

                    for(RepositoryCommit commit: repositoryCommits){
                        Date date = commit.getCommit().getCommitter().getDate();
                        Calendar cal = new GregorianCalendar();
                        cal.setTime(date);
                        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                        int time = cal.get(Calendar.HOUR_OF_DAY) % 24;
                        card[day][time]++;
                    }
                    for(int i=0;i<7;i++){

                        XYValueSeries s = new XYValueSeries("punchcard "+i);

                        for(int j = 0; j<23;j++){
                            s.add(j,i,card[i][j]);
                        }
                        dataset.addSeries(s);

                        XYSeriesRenderer xy = new XYSeriesRenderer();
                        xy.setColor(0xFF0000FF);
                        renderer.addSeriesRenderer(xy);

                    }

                    renderer.addYTextLabel(0, "Sundey");
                    renderer.addYTextLabel(1, "Mondey");
                    renderer.addYTextLabel(2, "Tuesday");
                    renderer.addYTextLabel(3, "Wednesday");
                    renderer.addYTextLabel(4, "Thursday");
                    renderer.addYTextLabel(5, "Friday");
                    renderer.addYTextLabel(6, "Saturday");

                    renderer.setYAxisMin(-0.5);
                    renderer.setYAxisMax(7.5);
                    renderer.setXAxisMin(-0.5);
                    renderer.setXAxisMax(24.5);

                    GraphicalView q = ChartFactory.getBubbleChartView(getActivity(), dataset, renderer);
                    graphLayout.removeAllViews();
                    graphLayout.addView(q);

                    graphLayout.setVisibility(View.VISIBLE);

                }
            }
        }.execute();


    }

}