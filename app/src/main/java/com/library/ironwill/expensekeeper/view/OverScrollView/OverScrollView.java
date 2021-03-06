package com.library.ironwill.expensekeeper.view.OverScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

public class OverScrollView extends ScrollView {

    public OverScrollView(Context context) {
        super(context);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int lastEventY;

/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int eventY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int yDistance = (int) getTranslationY();
                if (yDistance != 0 && listener != null) {
                    if (!listener.onOverScroll(yDistance, true)) { //only do this if listener returns false
                        animate().translationY(0)
                                .setDuration(200)
                                .setInterpolator(new DecelerateInterpolator(6))
                                .start();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                lastEventY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() == 0) {
                    handleOverScroll(event, false);
                } else {
                    View view = getChildAt(getChildCount() - 1);
                    if (view.getHeight() <= (getHeight() + getScrollY())) {
                        handleOverScroll(event, true);
                    }
                }
                break;
        }

        if (getTranslationY() != 0) {
            return true;
        }
        return super.onTouchEvent(event);

    }

    public interface OverScrollListener {
        boolean onOverScroll(int yDistance, boolean isReleased);
    }

    private OverScrollListener listener;
    public void setOverScrollListener(OverScrollListener listener) {
        this.listener = listener;
    }

    private void handleOverScroll(MotionEvent ev, boolean isBottom) {
        int pointerCount = ev.getHistorySize();
        for (int p = 0; p < pointerCount; p++) {
            int historicalY = (int) ev.getHistoricalY(p);
            int yDistance = (historicalY - lastEventY) / 6;

            if ((isBottom && yDistance < 0) || (!isBottom && yDistance > 0)) {
                setTranslationY(yDistance);
                if (listener != null) listener.onOverScroll(yDistance, false);
            }
        }
    }
}
