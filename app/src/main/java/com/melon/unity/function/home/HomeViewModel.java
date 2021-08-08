package com.melon.unity.function.home;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.commonlib.util.CommonUtil;
import com.melon.commonlib.util.Constant;
import com.melon.commonlib.util.LogUtil;
import com.melon.unity.AppApplication;
import com.melon.unity.function.password.PasswordActivity;
import com.melon.unity.listener.NetCallback;

import java.util.Timer;
import java.util.TimerTask;

public class HomeViewModel extends ViewModel {
    /**
     * 要搜索的内容
     */
    private final MutableLiveData<String> mContent;

    /**
     * http服务器在线状态
     */
    private final MutableLiveData<String> mServerStatus;
    private final HomeModel mMHomeModel;

    public HomeViewModel() {
        mContent = new MutableLiveData<>();
        mServerStatus = new MutableLiveData<>();
        mMHomeModel = new HomeModel();
    }

    public MutableLiveData<String> getContent() {
        return mContent;
    }

    public MutableLiveData<String> getServerStatus() {
        return mServerStatus;
    }

    /**
     * 浏览器搜索
     */
    public void search(Context ctx) {
        if (TextUtils.isEmpty(mContent.getValue())) {
            LogUtil.d("输入为空");
            return;
        }
        String url = Constant.URL_BAI_DU + mContent.getValue();
        CommonUtil.enterBrowser(ctx, url);
    }

    /**
     * 清空输入
     */
    public void deleteSearchContent() {
        mContent.setValue("");
    }

    /**
     * 标签
     */
    public String[] getTags() {
        return new String[]{"密码", "上海南站", "车墩站", "聊天", "电话本"};
    }

    public void enterTag(int position, Context ctx) {
        switch (position) {
            case 0:
                //查询密码
                CommonUtil.enterActivity(ctx, PasswordActivity.class);
                break;
            case 1:
                //查询金山铁路-上海南站
                CommonUtil.enterBrowser(ctx, "http://www.shjstl.com/lately.php?station=%E4%B8%8A%E6%B5%B7%E5%8D%97%E7%AB%99");
                break;
            case 2:
                //查询金山铁路-车墩站
                CommonUtil.enterBrowser(ctx, "http://www.shjstl.com/lately.php?station=%E8%BD%A6%E5%A2%A9");
                break;
            case 3:
                //聊天
                CommonUtil.enterBrowser(ctx, "http://192.168.100.234/topic");
                break;
            case 4:
                //电话本
                break;
            default:
        }
    }

//    private static final Timer mTimer = new Timer();
//    private TimerTask mTimerTask = new TimerTask() {
//
//        @Override
//        public void run() {
//            mMHomeModel.requestServerStatus(new NetCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    if ("true".equals(result)) {
//                        mServerStatus.postValue("在线中");
//                    }
//                }
//
//                @Override
//                public void onFail() {
//                    mServerStatus.postValue("不在线");
//                }
//            });
//        }
//    };

    @Override
    protected void onCleared() {
        super.onCleared();
//        mTimerTask.cancel();
//        mTimerTask = null;
//        LogUtil.d("任务取消");
    }

    /**
     * 网络请求服务器
     */
    public void requestServerStatus() {
        // 请求数据
//        mTimer.scheduleAtFixedRate(mTimerTask, 0, Constant.SERVER_HEART_BEAT_TIME);

        mMHomeModel.requestServerStatus(new NetCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if ("true".equals(result)) {
                    mServerStatus.postValue("在线中");
                }
            }

            @Override
            public void onFail() {
                mServerStatus.postValue("不在线");
            }
        });
    }
}