package com.melon.commonlib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/5/17.
 */
public class CommonUtil {
    /**
     * 启动Activity
     *
     * @param ctx   上下文
     * @param clazz 要跳转页面
     */
    public static void enterActivity(Context ctx, Class<?> clazz) {
        Intent intent = new Intent(ctx, clazz);
        if (ctx instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    /**
     * 启动Fragment
     *
     * @param ctx    上下文
     * @param target CommonFragmentActivity中定义的常量 目标fragment
     */
    public static void enterFragment(Context ctx, Class commonFragmentClazz, int target) {
        Intent intent = new Intent(ctx, commonFragmentClazz);
        intent.putExtra("target", target);
        if (ctx instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenHeight(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getScreenWidth(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 通过反射获取状态栏高度，默认25dp
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = dip2px(context, 25);
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
    //全屏
    public static void fullScreen(Activity act) {
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public static void cancelFullScreen(Activity act) {
        act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 空判断
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0 || "null".equalsIgnoreCase(str.toString()))
            return true;
        else
            return false;
    }

    /**
     * 空判断
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 计算图片高度
     */
    public static int getPicHeight(Context ctx, int img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), img, options);
        return options.outHeight;
    }

    /**
     * 复制到剪切板
     */
    public static void addToClipboard(Context ctx, String value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager cmb2 = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb2.setText(value);
            ToastUtil.toast("已复制");
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     * @param flag     true    隐藏
     */
    public static void hideInputMode(Activity activity, boolean flag) {
        if (flag) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 时间截转日期、时间
     *
     * @param time 秒
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(String time) {
        String date = "";
        try {
            date = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(new Date(Long.parseLong(time) * 1000)); // * 1000
        } catch (Exception e) {
            if (!isEmpty(time)) {
                return time;
            }
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 友好时间
     *
     * @param time 秒
     * @return
     */
    public static String getMyDateFormat(String time) {
        String result = "未知";
        try {
            long dataTime = Long.parseLong(time) * 1000;

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            String today = sdfDate.format(new Date());
            long zeroTime = sdfDate.parse(today).getTime(); // 今天0点，毫秒
            long dTime = zeroTime - dataTime; // 时间间隔
            if (dTime < 0) {
                result = "今天 " + sdfTime.format(dataTime);
            } else if (dTime < 86400000) {
                result = "昨天 " + sdfTime.format(dataTime);
            } else if (dTime < 86400000 * 6) {
                int day = (int) (dTime / 86400000);
                result = (day + 1) + "天前";
            } else if (dTime < 86400000 * 13) {
                result = "1周前";
            } else if (dTime < 86400000 * 20) {
                result = "2周前";
            } else if (dTime < 86400000 * 27L) {
                result = "3周前";
//            } else {
//                result = "更早";
            } else {
                result = getDateTime(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setTransparentStateBar(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = act.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            act.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 手机号校验
     */
    public static boolean isMobileChina(String phone) {
        // 验证手机号
        Pattern p = Pattern.compile("^[1][3,4,5, 6,7,8][0-9]{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 系统下载文件
     *
     * @param url 完整下载地址
     * @return APK存储的路径
     */
    public static String downloadFile(Context ctx, String url) {
        int start = url.lastIndexOf("/");
        String fileName = url.substring(start + 1);

        DownloadManager downloadManager = ((DownloadManager) ctx.getSystemService(Activity.DOWNLOAD_SERVICE));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 在通知栏中显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //文件存储路径 绝对路径
        request.setDestinationInExternalFilesDir(ctx, Environment.DIRECTORY_DOWNLOADS, fileName);
        //下载时在通知栏显示的文字
        request.setTitle(fileName);

        //执行下载
        downloadManager.enqueue(request);

        return ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
    }

    /**
     * 网页分享
     *
     * @param context 上下文
     * @param url     分享内容
     */
    public static void shareWebUrl(Context context, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "Share To"));
    }

    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }
    }
}
