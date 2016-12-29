package com.library.ironwill.expensekeeper.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.adapter.RvCategoryAdapter;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.model.ItemCategory;
import com.library.ironwill.expensekeeper.util.Navigator;
import com.library.ironwill.expensekeeper.view.ExplosionView.ExplosionField;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.IronItemAnimator;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.IronRecyclerView;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.RecyclerViewClickListener;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.SimpleDividerItemDecoration;
import com.library.ironwill.expensekeeper.view.RandomTextView.RandomTextView;

import java.util.ArrayList;

public class CardListFragment extends TransitionHelper.BaseFragment{ // implements OnStartDragListener

    private IronRecyclerView mRecyclerView;
    private RvCategoryAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ItemCategory mCategory;
    private ArrayList<ItemCategory> mList;
    private RandomTextView rtvIncome, rtvExpense;
    private ItemTouchHelper mItemTouchHelper;


    private static int[] digits = new int[] {10,9,8,7,6};

    public CardListFragment() {}

    protected ArrayList<ItemCategory> getList() {
        mList = new ArrayList<>();
        mCategory = new ItemCategory(R.mipmap.ic_launcher, "FreeLancing", "$ " + 235, 1);
        mList.add(0, mCategory);
        mCategory = new ItemCategory(R.mipmap.ic_launcher, "Food", "$ " + 40, 0);
        mList.add(1, mCategory);
        mCategory = new ItemCategory(R.mipmap.ic_launcher, "Movie", "$ " + 30, 0);
        mList.add(2, mCategory);
        mCategory = new ItemCategory(R.mipmap.ic_launcher, "Gym", "$ " + 10, 0);
        mList.add(3, mCategory);
        mCategory = new ItemCategory(R.mipmap.ic_launcher, "Gas", "$ " + 50, 0);
        mList.add(4, mCategory);
        return mList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_list, container, false);
        mRecyclerView = (IronRecyclerView) rootView.findViewById(R.id.recycler_list);
        mRecyclerView.setFocusable(false);

        ExplosionField explosionField = new ExplosionField(getActivity());
        explosionField.addListener(rootView.findViewById(R.id.cardView_main));

        rtvIncome = (RandomTextView) rootView.findViewById(R.id.rtv_income);
        rtvExpense = (RandomTextView) rootView.findViewById(R.id.rtv_expense);
        rtvIncome.setText("235");
        rtvIncome.setPianyilian(digits);
        rtvIncome.start();
        rtvExpense.setText("130");
        rtvExpense.setPianyilian(digits);
        rtvExpense.start();
        initRecyclerList();
        return rootView;
    }

    private void initRecyclerList() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        mAdapter = new RvCategoryAdapter();
        mAdapter.updateList(getList(),getActivity());

        /*mAdapter.setOnItemClickListener(new RvCategoryAdapter.OnItemClickListener<ItemCategory>() {

            @Override
            public void onItemClick(View view, ItemCategory item, boolean isLongClick) {
                if (isLongClick) {
                    MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.X);
                } else {
                    Navigator.launchDetail(MainActivity.of(getActivity()), view, item, mRecyclerView);
                }
            }
        });*/
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Navigator.launchDetail(MainActivity.of(getActivity()), view, mRecyclerView);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                MainActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.X);
            }
        }));

        /*mAdapter.setOnRVItemLongClickListener(new RvCategoryAdapter.onRVItemLongClickListener() {
            @Override
            public void onRVItemLongClick(int position) {
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            }
        });*/
        mRecyclerView.setAdapter(mAdapter);
        //setup the Item Add/Remove Animation
        IronItemAnimator mIronItemAnimator= new IronItemAnimator();
        mIronItemAnimator.setAddDuration(1500);
        mIronItemAnimator.setRemoveDuration(700);
        mRecyclerView.setItemAnimator(mIronItemAnimator);
    }

    @Override
    public boolean onBeforeBack() {
        MainActivity activity = MainActivity.of(getActivity());
       /* if (!activity.animateHomeIcon(MaterialMenuDrawable.IconState.BURGER)) {
            activity.mDrawer.openDrawer(GravityCompat.START);
        }*/
        return super.onBeforeBack();
    }


/*    @Override
    public void onStartDrag(RvCategoryAdapter.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }*/
}

