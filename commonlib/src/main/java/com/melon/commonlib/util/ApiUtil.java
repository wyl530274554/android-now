package com.melon.commonlib.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;


/**
 * 接口地址管理
 *
 * @author melon.wang
 * 2019/1/7 17:28
 */
public class ApiUtil {
    private static final String API_PROTOCOL = "http://";
    public static String API_IP = "192.168.31.234";
    private static final String API_PORT = "8080";
    public static String API_BASE;

    public static final String API_CONTACTS = "contacts/";
    public static final String API_PASSWORD = "password/";
    public static final String API_TOPIC = "topic/";
    public static final String API_MONEY = "money/";

    public static final String APP_DOWNLOAD = "files/";

    public static void initIp(Context ctx) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String ip = preferences.getString("ip", "");
        if (!TextUtils.isEmpty(ip)) {
            API_IP = ip;
        }

        if (ip != null && ip.contains(":")) {
            API_BASE = API_PROTOCOL + API_IP + "/";
        } else {
            API_BASE = API_PROTOCOL + API_IP + ":" + API_PORT + "/";
        }
    }

}
