package com.library.ironwill.expensekeeper.view.IronRecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.adapter.RvCategoryAdapter;


public class IronRecyclerView extends RecyclerView {

    private int xDown, xMove, yDown, yMove, mTouchSlop;
    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private Scroller mScroller;
    private ImageView imageView;
    private boolean isFirst = true;


    public IronRecyclerView(Context context) {
        this(context, null);
    }

    public IronRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IronRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        maxLength = ((int) (190 * context.getResources().getDisplayMetrics().density + 0.5f));
        mScroller = new Scroller(context, new LinearInterpolator(context, null));

    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            pos = mFirstPosition + i;
                        }
                    }
                }
                View view = getChildAt(pos - mFirstPosition);
                if (null != view) {
                    RvCategoryAdapter.ViewHolder viewHolder = (RvCategoryAdapter.ViewHolder) getChildViewHolder(view);
//                    itemLayout = viewHolder.mContainer;
                    imageView = (ImageView) itemLayout.findViewById(R.id.iv_delete);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }
                    if (scrollX > maxLength / 2) {
                        imageView.setVisibility(VISIBLE);
                        if (isFirst) {
                            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.4f, 1f);
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.4f, 1f);
                            AnimatorSet animSet = new AnimatorSet();
                            animSet.play(animatorX).with(animatorY);
                            animSet.setDuration(800);
                            animSet.start();
                            isFirst = false;
                        }
                    } else {
                        imageView.setVisibility(GONE);
                    }
                    itemLayout.scrollBy(newScrollX, 0);
                }
                break;

            case MotionEvent.ACTION_UP:

                int scrollX = itemLayout.getScrollX();
                if (scrollX > maxLength / 2) {
                    ((RvCategoryAdapter) getAdapter()).removeRecycle(pos);
                    mScroller.startScroll(0, 0, 0, 0);
                } else {
                    mScroller.startScroll(scrollX, 0, -scrollX, 0);
                }
                invalidate();
                isFirst = true;
                break;
        }
        mStartX = x;
        return super.onTouchEvent(event);
    }

    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
