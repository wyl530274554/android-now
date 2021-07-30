package com.melon.unity.function.settings;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.commonlib.util.LogUtil;
import com.melon.unity.net.SocketClient;

import java.util.Timer;
import java.util.TimerTask;


/**
 * VM负责
 * 1、为Activity、Fragment提供数据(liveData)
 * 2、数据处理业务逻辑
 * 3、获取数据或转发用户请求修改数据
 * <p>
 * 禁止持有View对象
 * <p>
 * extends ViewModel 具备生命周期感知能力
 *
 * <p>
 * 参考：https://developer.android.google.cn/jetpack/guide#fetch-data
 */
public class SettingsViewModel extends ViewModel {
    private final SettingsModel mModel = new SettingsModel();
    //可观测数据
    private final MutableLiveData<Boolean> onlineState = new MutableLiveData<>();

    public MutableLiveData<Boolean> getOnlineState() {
        return onlineState;
    }

    public void initOnlineState() {
        //查询一次状态
        boolean online = mModel.isOnline();
        LogUtil.d("online: " + online);
        onlineState.setValue(online);
    }
}
