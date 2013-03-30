package com.github.graph.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.github.graph.R;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.widget.BarGraphDrawable;
import org.eclipse.egit.github.core.Contributor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yarik
 * Date: 3/30/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarChartFragment extends Fragment {


    private ImageView graph;
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_fragment, container, false);
        graph = (ImageView) view.findViewById(R.id.graph);
        list = (ListView) view.findViewById(R.id.list);
        return view;
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

    public void setContributors(List<Contributor> contributors) {
        ContributorAdapter adapter = new ContributorAdapter(this.getActivity(), contributors);
        list.setAdapter(adapter);


        int[] data = new int[contributors.size()];
        for (int i = 0; i < contributors.size(); i++) {
            data[i] = contributors.get(i).getContributions();
        }
        setData(data);
    }
}
