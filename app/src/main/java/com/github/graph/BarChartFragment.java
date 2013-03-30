package com.github.graph;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        return view;
    }
}
