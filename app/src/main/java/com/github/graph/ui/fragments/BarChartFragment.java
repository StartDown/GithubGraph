package com.github.graph.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.graph.R;
import com.github.graph.ui.widget.BarGraphDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: yarik
 * Date: 3/30/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarChartFragment extends Fragment {


    private TextView username;
    private TextView repository;

    private ImageView avatar;
    private ImageView graph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_fragment, container, false);
        username = (TextView) view.findViewById(R.id.username);
        repository = (TextView) view.findViewById(R.id.repository);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        graph = (ImageView) view.findViewById(R.id.graph);

        long[][] data = {{1,2}, {3,4}};
        int[][] colors = {{1,2}, {3,4}};
        graph.setImageDrawable(new BarGraphDrawable(data,colors));
        return view;
    }
}
