package com.melon.commonlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Scroller;

import com.melon.commonlib.util.CommonUtil;

/**
 * Created by mleon on 2016/6/14.
 */
public class MyListView extends ListView {
    private int screenHeight;
    private GestureDetector mDetector;
    private Scroller mScroller;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (screenHeight == 0)
            screenHeight = CommonUtil.getScreenHeight(getContext());

        mScroller = new Scroller(context);
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                scrollTo(0, (int) distanceY);
                return super.onScroll(e1, e2, distanceX, distanceX);
            }

        });
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    private int startY;
    private int dy; //移动总距离

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (dy > 0) {
                    mScroller.startScroll(0, dy, 0, -dy, 1000);
                    invalidate();
                } else {
                    scrollTo(0, 0);
                }
                dy = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) ev.getY();
                int tempDy = tempY - startY;
                dy += tempDy;
                scrollBy(0, -tempDy);

                startY = (int) ev.getY();
                break;
        }
//        mDetector.onTouchEvent(ev);
        return true;
    }
}
