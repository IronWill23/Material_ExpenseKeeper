package com.library.ironwill.expensekeeper.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.model.BarModel;
import com.library.ironwill.expensekeeper.view.statisticView.BarChart;

import java.util.ArrayList;
import java.util.List;


public class BarChartFragment extends Fragment {

    private View rootView;
    private BarChart barChart;

    public static BarChartFragment create() {
        BarChartFragment f = new BarChartFragment();
        return f;
    }

    public BarChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_barchart, container, false);
        initView();

        List<BarModel> dataEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BarModel entity = new BarModel(String.valueOf(i), (float) (Math.random() * 1000));
            dataEntities.add(entity);
        }
        barChart.setData(dataEntities);

        return rootView;
    }

    private void initView() {
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);
    }

}
