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
    public static String API_IP = "192.168.100.234";
    private static final String API_PORT = "80";
    public static String API_BASE;

    public static final String API_NOTE_ADD = "note/addNote";
    public static final String API_NOTE_DEL = "note/delNote";
    public static final String API_NOTE_UPDATE = "note/updateNote";
    public static final String API_NOTE_QUERY = "note/queryNote";
    public static final String API_NOTE_ALL = "note/queryAllNote";
    public static final String API_GET_ALL_IMAGE = "index/allImages";

    public static final String API_CONTACTS = "contacts/";
    public static final String API_PASSWORD = "password/";
    public static final String API_APP_UPGRADE = "/upgrade";

    public static final String APP_DOWNLOAD = "file/";

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
