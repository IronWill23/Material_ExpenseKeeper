package com.library.ironwill.expensekeeper.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.adapter.RvCategoryAdapter;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.model.ItemCategory;
import com.library.ironwill.expensekeeper.util.Navigator;
import com.library.ironwill.expensekeeper.view.ExplosionView.ExplosionField;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.HidingScrollListener;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.IronItemAnimator;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.IronRecyclerView;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.RecyclerViewClickListener;
import com.library.ironwill.expensekeeper.view.IronRecyclerView.SimpleDividerItemDecoration;
import com.library.ironwill.expensekeeper.view.RandomTextView.RandomTextView;

import java.util.ArrayList;

public class CardListFragment extends TransitionHelper.BaseFragment implements RvCategoryAdapter.OnRemoveItemListener{ // implements OnStartDragListener

    private IronRecyclerView mRecyclerView;
    private RvCategoryAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ItemCategory mCategory;
    private static ArrayList<ItemCategory> mList;
    private RandomTextView rtvIncome, rtvExpense;
    private ItemTouchHelper mItemTouchHelper;

    public FloatingActionButton addFABtn, doneFABtn;
    private BottomSheetBehavior mBehavior;
    private View mBottomSheet;
    private EditText titleText, contentText;

    private int lastVisibleItem;
    private int totalItemCount;


    public CardListFragment() {
    }

    protected ArrayList<ItemCategory> getList() {
        mList = new ArrayList<>();
        mCategory = new ItemCategory(R.drawable.salary, "Salary", "$ " + 235, 1);
        mList.add(0, mCategory);
        mCategory = new ItemCategory(R.drawable.food, "Food", "$ " + 40, 0);
        mList.add(1, mCategory);
        mCategory = new ItemCategory(R.drawable.movie, "Movie", "$ " + 30, 0);
        mList.add(2, mCategory);
        mCategory = new ItemCategory(R.drawable.gym, "Gym", "$ " + 10, 0);
        mList.add(3, mCategory);
        mCategory = new ItemCategory(R.drawable.gas, "Gas", "$ " + 50, 0);
        mList.add(4, mCategory);
        mCategory = new ItemCategory(R.drawable.rent, "Rent", "$ " + 30, 0);
        mList.add(5, mCategory);
        return mList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_list, container, false);
        mRecyclerView = (IronRecyclerView) rootView.findViewById(R.id.recycler_list);
        addFABtn = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        doneFABtn = (FloatingActionButton) rootView.findViewById(R.id.fab_done);
        mBottomSheet = rootView.findViewById(R.id.id_rl_bottomSheet);
        titleText = (EditText) rootView.findViewById(R.id.id_et_new_name);
        contentText = (EditText) rootView.findViewById(R.id.id_et_new_num);
        mRecyclerView.setFocusable(false);

        ExplosionField explosionField = new ExplosionField(getActivity());
        explosionField.addListener(rootView.findViewById(R.id.cardView_main));

        rtvIncome = (RandomTextView) rootView.findViewById(R.id.rtv_income);
        rtvExpense = (RandomTextView) rootView.findViewById(R.id.rtv_expense);
        rtvIncome.setText("235");
        rtvIncome.setPianyilian(RandomTextView.FIRSTF_FIRST);
        rtvIncome.start();
        rtvExpense.setText("160");
        rtvExpense.setPianyilian(RandomTextView.FIRSTF_FIRST);
        rtvExpense.start();
        initRecyclerList();
        initFBtnAction();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rtvExpense.destroy();
        rtvIncome.destroy();
    }

    private void initRecyclerList() {
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        mAdapter = new RvCategoryAdapter();
        mAdapter.updateList(getList(), getActivity());
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
        mRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onShow() {
                addFABtn.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
            @Override
            public void onHide() {
                /*RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) addFABtn.getLayoutParams();
                int fabMargin = lp.bottomMargin;*/
                addFABtn.animate().translationY(400)
                        .setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });

        /*mAdapter.setOnRVItemLongClickListener(new RvCategoryAdapter.onRVItemLongClickListener() {
            @Override
            public void onRVItemLongClick(int position) {
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            }
        });*/
        mRecyclerView.setAdapter(mAdapter);
        IronItemAnimator mIronItemAnimator = new IronItemAnimator();
        mIronItemAnimator.setAddDuration(1500);
        mIronItemAnimator.setRemoveDuration(700);
        mRecyclerView.setItemAnimator(mIronItemAnimator);
    }


    @Override
    public void onRemoveListener() {
        lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        totalItemCount = linearLayoutManager.getItemCount();
        if (lastVisibleItem == totalItemCount - 1){
            addFABtn.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        }
    }

    private void initFBtnAction() {
        /**
         * BottomSheet Behavior programming
         */
        if (mBottomSheet != null) {
            mBehavior = BottomSheetBehavior.from(mBottomSheet);
        }

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (mBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING) {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    doneFABtn.setVisibility(View.VISIBLE);
                } else {
                    doneFABtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        addFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    openMenu(view);
                } else {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    closeMenu(view);
                }
            }

            private void openMenu(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 65, 135);
                animator.setDuration(600);
                animator.start();
            }

            private void closeMenu(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 135, 65, 0);
                animator.setDuration(600);
                animator.start();
            }
        });
        doneFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleText.getText().toString().equals("") && !contentText.getText().toString().equals("")) {

                    mList.add(new ItemCategory(
                            R.mipmap.ic_launcher,
                            "$ " + titleText.getText().toString(),
                            contentText.getText().toString(),
                            0
                    ));
                    mAdapter = new RvCategoryAdapter();
                    mAdapter.updateList(mList, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    titleText.setText("");
                    contentText.setText("");
                } else {
                    Toast.makeText(getActivity(), "Title and Content are null",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onBeforeBack() {
//        MainActivity activity = MainActivity.of(getActivity());
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

