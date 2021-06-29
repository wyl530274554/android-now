package com.melon.commonlib.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class SystemUtils {
    /**
     * 设备信息
     */
    public static String getDeviceInfo() {
        String sb = ("主板：" + Build.BOARD) +
                "\n系统启动程序版本号：" + Build.BOOTLOADER +
                "\n系统定制商：" + Build.BRAND +
                "\ncpu指令集：" + Build.CPU_ABI +
                "\ncpu指令集2：" + Build.CPU_ABI2 +
                "\n设置参数：" + Build.DEVICE +
                "\n显示屏参数：" + Build.DISPLAY +
                "\n无线电固件版本：" + Build.getRadioVersion() +
                "\n硬件识别码：" + Build.FINGERPRINT +
                "\n硬件名称：" + Build.HARDWARE +
                "\nHOST:" + Build.HOST +
                "\n修订版本列表：" + Build.ID +
                "\n硬件制造商：" + Build.MANUFACTURER +
                "\n版本：" + Build.MODEL +
                "\n硬件序列号：" + Build.SERIAL +
                "\n手机制造商：" + Build.PRODUCT +
                "\n描述Build的标签：" + Build.TAGS +
                "\nTIME:" + Build.TIME +
                "\nbuilder类型：" + Build.TYPE +
                "\nUSER:" + Build.USER;
        return sb;
    }

    public static String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
