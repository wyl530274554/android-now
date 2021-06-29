package com.melon.commonlib.view;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.melon.commonlib.util.LogUtil;

/**
 * Created by admin on 2017/3/2.
 * Email 530274554@qq.com
 */

public class MyPressTouchListener implements View.OnTouchListener {
    public final float[] BT_SELECTED = new float[] {
            1, 0, 0, 0, -20,
            0, 1, 0, 0, -20,
            0, 0, 1, 0, -20,
            0, 0, 0, 1, 0 };
    public final float[] BT_NOT_SELECTED = new float[] {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0 };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogUtil.e("ACTION: "+event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtil.e("ACTION_DOWN");
            if(v instanceof ImageView){
                ImageView iv = (ImageView) v;
                iv.setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) ) ;
                LogUtil.e("ImageView BT_SELECTED");
            }else{
                v.getBackground().setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) );
                v.setBackgroundDrawable(v.getBackground());
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            LogUtil.e("ACTION_UP");
            if(v instanceof ImageView){
                ImageView iv = (ImageView) v;
                iv.setColorFilter( new ColorMatrixColorFilter(BT_NOT_SELECTED) ) ;
                LogUtil.e("ImageView BT_NOT_SELECTED");
            }else{
                v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_NOT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());
            }
        }
        return false;
    }
}
