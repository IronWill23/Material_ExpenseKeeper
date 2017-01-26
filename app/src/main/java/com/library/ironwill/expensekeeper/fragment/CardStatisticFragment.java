package com.library.ironwill.expensekeeper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.adapter.TabAdapter;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.util.Navigator;
import com.library.ironwill.expensekeeper.view.OverScrollView.OverScrollView;

import java.util.ArrayList;


public class CardStatisticFragment extends TransitionHelper.BaseFragment {


    private int[] mColors = {0xFF75C3FF, 0xFFFFD700, 0xFFE32636, 0xFF4CCC97, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private OverScrollView mScrollView;
    private View rootView;
    private TabLayout mTab;
    private ViewPager mPager;
    private PieChartFragment pieChartFragment;
    private BarChartFragment barChartFragment;
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;


    public static CardStatisticFragment create() {
        CardStatisticFragment f = new CardStatisticFragment();
        return f;
    }

    public CardStatisticFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistic_detail, container, false);
        initControls(rootView);
        mScrollView = (OverScrollView) rootView.findViewById(R.id.overscroll_view);

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

//        initDetailBody();
        return rootView;
    }

    private void initControls(View view) {
        mTab = (TabLayout) view.findViewById(R.id.tab_Fragment_title);
        mPager = (ViewPager) view.findViewById(R.id.vp_Fragment_pager);

        //初始化各fragment
        pieChartFragment = new PieChartFragment();
        barChartFragment = new BarChartFragment();

        //将fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(pieChartFragment);
        listFragment.add(barChartFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle = new ArrayList<>();
        listTitle.add("PieChart");
        listTitle.add("BarChart");

        //设置TabLayout的模式
        mTab.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTab.addTab(mTab.newTab().setText(listTitle.get(0)));
        mTab.addTab(mTab.newTab().setText(listTitle.get(1)));

        TabAdapter mAdapter = new TabAdapter(getActivity().getSupportFragmentManager(), listFragment, listTitle);

        //viewpager加载adapter
        mPager.setAdapter(mAdapter);
        //mTab.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mTab.setupWithViewPager(mPager);
        //mTab.set
    }

/*    private void initDetailBody() {
        pieChart.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                pieChart.animate().alpha(1).start();
            }
        }, 500);
    }*/

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
        TransitionHelper.fadeThenFinish(mScrollView, getActivity());
        return false;
    }
}
