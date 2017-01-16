package com.library.ironwill.expensekeeper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.model.PieModel;
import com.library.ironwill.expensekeeper.util.Navigator;
import com.library.ironwill.expensekeeper.view.OverScrollView.OverScrollView;
import com.library.ironwill.expensekeeper.view.statisticView.PieChart;

import java.util.ArrayList;
import java.util.List;


public class CardStatisticFragment extends TransitionHelper.BaseFragment {


    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private OverScrollView mScrollView;
    private View rootView;
    private PieChart pieChart;

    public static CardStatisticFragment create() {
        CardStatisticFragment f = new CardStatisticFragment();
        return f;
    }

    public CardStatisticFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistic_detail, container, false);
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

        mScrollView.setOverScrollListener(new OverScrollView.OverScrollListener() {
            int translationThreshold = 100;

            @Override
            public boolean onOverScroll(int yDistance, boolean isReleased) {
                if (Math.abs(yDistance) > translationThreshold) { //passed threshold
                    if (isReleased) {
                        getActivity().onBackPressed();
                        return true;
                    } else {
                        MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.X);
                    }
                } else {
                    MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.ARROW);
                }
                return false;
            }
        });

        initDetailBody();
        return rootView;
    }

    private void initView() {
        mScrollView = (OverScrollView) rootView.findViewById(R.id.overscroll_view);
        pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
    }

    private void initDetailBody() {
        pieChart.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                pieChart.animate().alpha(1).start();
            }
        }, 500);
    }

    @Override
    public void onBeforeViewShows(View contentView) {
        ViewCompat.setTransitionName(mScrollView, "detail_element");
        TransitionHelper.excludeEnterTarget(getActivity(), R.id.toolbar_container, true);
        TransitionHelper.excludeEnterTarget(getActivity(), R.id.full_screen, true);
    }

    @Override
    public void onBeforeEnter(View contentView) {
        MainActivity.of(getActivity()).fragmentBackground.animate().scaleX(.92f).scaleY(.92f).alpha(.6f).setDuration(Navigator.ANIM_DURATION).setInterpolator(new AccelerateInterpolator()).start();
        MainActivity.of(getActivity()).setHomeIcon(MaterialMenuDrawable.IconState.BURGER);
        MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.ARROW);
    }

    @Override
    public boolean onBeforeBack() {
        MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.BURGER);
        MainActivity.of(getActivity()).fragmentBackground.animate().scaleX(1).scaleY(1).alpha(1).translationY(0).setDuration(Navigator.ANIM_DURATION).setInterpolator(new DecelerateInterpolator()).start();
        TransitionHelper.fadeThenFinish(pieChart, getActivity());
        return false;
    }
}
