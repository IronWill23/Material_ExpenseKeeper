package com.library.ironwill.expensekeeper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.util.Navigator;
import com.library.ironwill.expensekeeper.view.OverScrollView.OverScrollView;


public class CardDetailFragment extends TransitionHelper.BaseFragment {

    
    private TextView tvTitle, tvTextBody;
    private OverScrollView mScrollView;
    private View rootView;

    public static CardDetailFragment create() {
        CardDetailFragment f = new CardDetailFragment();
        return f;
    }

    public CardDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_detail, container, false);
        initView();
//        String itemText = getActivity().getIntent().getStringExtra("item_text");
        String itemText = "Salary";
        tvTitle.setText(itemText);

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

    private void initView(){
        mScrollView = (OverScrollView) rootView.findViewById(R.id.overscroll_view);
        tvTitle = (TextView) rootView.findViewById(R.id.detail_title);
        tvTextBody = (TextView) rootView.findViewById(R.id.detail_body);
    }

    private void initDetailBody() {
        tvTextBody.setAlpha(0);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                tvTextBody.animate().alpha(1).start();
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
        TransitionHelper.fadeThenFinish(tvTextBody, getActivity());
        return false;
    }
}
