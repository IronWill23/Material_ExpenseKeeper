package com.library.ironwill.expensekeeper.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.model.PieModel;
import com.library.ironwill.expensekeeper.view.statisticView.PieChart;

import java.util.ArrayList;
import java.util.List;


public class PieChartFragment extends Fragment {


    private int[] mColors = {0xFF75C3FF, 0xFFFFD700, 0xFFE32636, 0xFF4CCC97, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private View rootView;
    private PieChart pieChart;

    public static PieChartFragment create() {
        PieChartFragment f = new PieChartFragment();
        return f;
    }

    public PieChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_piechart, container, false);
        initView();

        List<PieModel> dataEntities = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PieModel entity = new PieModel("name" + i, i + 1, mColors[i]);
            dataEntities.add(entity);
        }
        pieChart.setDataList(dataEntities);
        pieChart.setOnItemPieClickListener(new PieChart.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
        return rootView;
    }

    private void initView() {
        pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
    }
}
