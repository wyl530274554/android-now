package com.melon.commonlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.melon.commonlib.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/11/22.
 * Email 530274554@qq.com
 */

public class MyBoxView extends View {
    private Box mCurrentBox;
    private List<Box> mBoxs = new ArrayList<>();
    private Paint mBackGroundPaint;
    private Paint mBoxPaint;

    public MyBoxView(Context context) {
        super(context);
        init();

    }

    public MyBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(0xff000000);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x88ff0000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF currentP = new PointF(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("down");
                mCurrentBox = new Box(currentP);
                mBoxs.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("move");
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrentP(currentP);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("up");
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.e("cancel");
                mCurrentBox = null;
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //TODO 画画布的背景
        canvas.drawPaint(mBackGroundPaint);
        //画所有的矩形
        for (Box box : mBoxs) {
            float l = Math.min(box.getOriginP().x, box.getCurrentP().x);
            float t = Math.min(box.getOriginP().y, box.getCurrentP().y);
            float r = Math.max(box.getOriginP().x, box.getCurrentP().x);
            float b = Math.max(box.getOriginP().y, box.getCurrentP().y);
            canvas.drawRect(l, t, r, b, mBoxPaint);
        }
    }

    public class Box {
        private PointF originP;
        private PointF currentP;

        public Box(PointF origin) {
            this.originP = origin;
            this.currentP = origin;
        }

        public PointF getOriginP() {
            return originP;
        }

        public void setOriginP(PointF originP) {
            this.originP = originP;
        }

        public PointF getCurrentP() {
            return currentP;
        }

        public void setCurrentP(PointF current) {
            this.currentP = current;
        }
    }
}
