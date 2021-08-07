package com.melon.unity.function.home;

import com.melon.commonlib.util.HttpUtil;
import com.melon.commonlib.util.LogUtil;
import com.melon.unity.listener.NetCallback;

public class HomeModel {

    public void requestServerStatus(NetCallback<String> stringNetCallback) {
        HttpUtil.doGet("", new HttpUtil.HttpCallbackStringListener() {
            @Override
            public void onFinish(String response) {
                stringNetCallback.onSuccess(response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e("requestServerStatus error: " + e.getMessage());
                stringNetCallback.onFail();
            }
        });
    }
}
