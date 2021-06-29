package com.melon.commonlib.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;


/**
 * 网络相关
 *
 * @author Melon
 */
public class NetUtil {

    private static final String TAG = "NetUtil";

    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    /**
     * wap网络
     */
    public static final int NETWORKTYPE_WAP = 1;
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORKTYPE_3G = 3;
    public static final int NETWORKTYPE_4G = 4;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 5;

    /**
     * wifi按信号，排序
     */
    public static void sort(List<ScanResult> mWifis) {
        // 大->小
        Collections.sort(mWifis, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                return rhs.level - lhs.level;
            }
        });
    }

    /**
     * 判断WIFI是否打开
     *
     * @return true如果打开;
     */
    public static boolean isWifiEnable(Context ctx) {
        WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        return wm.isWifiEnabled();
    }

    /**
     * 判断WIFI是否连接
     */
    public static boolean isWifiConnected(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            return info.isConnected();
        }
        return false;
    }
}
