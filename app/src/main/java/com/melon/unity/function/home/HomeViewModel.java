package com.melon.unity.function.home;

import static com.melon.commonlib.util.ApiUtil.API_BASE;
import static com.melon.commonlib.util.ApiUtil.API_TOPIC;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.melon.commonlib.util.CommonUtil;
import com.melon.commonlib.util.Constant;
import com.melon.commonlib.util.LogUtil;
import com.melon.commonlib.util.NetUtil;
import com.melon.unity.function.money.MoneyActivity;
import com.melon.unity.function.password.PasswordActivity;
import com.melon.unity.listener.NetCallback;

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

        //获取选择的搜索引擎
        String url;
        String engineStr = PreferenceManager.getDefaultSharedPreferences(ctx).getString("engine", "0");
        int engine = Integer.parseInt(engineStr);
        LogUtil.d("engine: " + engine);
        if (engine == 0 || NetUtil.isWifiConnected(ctx)) {
            url = Constant.URL_BAI_DU + mContent.getValue();
        } else {
            url = Constant.URL_BING + mContent.getValue();
        }
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
        return new String[]{"密码", "上海南站", "车墩站", "聊天", "天气", "金鸡"};
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
                CommonUtil.enterBrowser(ctx, API_BASE + API_TOPIC);
                break;
            case 4:
                //天气
                CommonUtil.enterBrowser(ctx, Constant.URL_BAI_DU + "天气");
                break;
            case 5:
                //金鸡
                CommonUtil.enterActivity(ctx, MoneyActivity.class);
                break;
            default:
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * 网络请求服务器
     */
    public void requestServerStatus() {
        // 请求数据
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