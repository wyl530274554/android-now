package com.melon.commonlib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.melon.commonlib.R;
import com.melon.commonlib.view.LoadingDialog;
/**
 * 对话框处理
 * @author melon.wang
 * @date 2019/1/17 16:49
 * Email 530274554@qq.com
 */
public class DialogUtil {
    /**
     * 全透明对话框
     * 作用：防止乱点击
     */
    public static Dialog getTransparentDialog(Activity act) {
        Dialog dialog = new Dialog(act, R.style.CustomTransparentDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showProgressDialog(Activity act) {
        LoadingDialog dialog = new LoadingDialog(act);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        if (!act.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 从下而上弹出对话框
     */
    public static Dialog getDown2UpDialog(Activity act, int contentView) {
        Dialog dialog = new Dialog(act, R.style.Down2UpDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);

        // 动画弹出
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 0.8f;
        lp.width = act.getWindowManager().getDefaultDisplay().getWidth();
        window.setWindowAnimations(R.style.Down2UpDialogAnimStyle);

        return dialog;
    }

    /**
     * 从下而上弹出对话框
     */
    public static Dialog getDown2UpDialog(Activity act, int contentView, int height) {
        Dialog dialog = new Dialog(act, R.style.Down2UpDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);

        // 动画弹出
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 0.8f;
        lp.width = act.getWindowManager().getDefaultDisplay().getWidth();
        lp.height = height;
        window.setWindowAnimations(R.style.Down2UpDialogAnimStyle);

        return dialog;
    }

    /**
     * 系统自带提示对话框    只有内容，没有按钮，没有提示标题
     */
    public static void showMsgOnly(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * 普通的带确定/取消
     */
    public static void show(Activity activity, String message, DialogInterface.OnClickListener okListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", okListener);
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
