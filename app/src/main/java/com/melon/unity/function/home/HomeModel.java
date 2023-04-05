package com.melon.unity.function.home;

import androidx.lifecycle.MutableLiveData;

import com.melon.commonlib.util.HttpUtil;
import com.melon.commonlib.util.LogUtil;

public class HomeModel {

    public void requestServerStatus(MutableLiveData<String> mServerStatus) {
        HttpUtil.doGet("", new HttpUtil.HttpCallbackStringListener() {
            @Override
            public void onFinish(String response) {
                mServerStatus.postValue("在线中");
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e("requestServerStatus error: " + e.getMessage());
                mServerStatus.postValue("离线中");
            }
        });
    }
}
