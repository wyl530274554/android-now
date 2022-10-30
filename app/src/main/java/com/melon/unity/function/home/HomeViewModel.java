package com.melon.unity.function.home;

import static com.melon.commonlib.util.ApiUtil.API_BASE;
import static com.melon.commonlib.util.ApiUtil.API_TOPIC;
import static com.melon.commonlib.util.ApiUtil.APP_DOWNLOAD;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.melon.commonlib.util.CommonUtil;
import com.melon.commonlib.util.Constant;
import com.melon.commonlib.util.LogUtil;
import com.melon.commonlib.util.NetUtil;
import com.melon.unity.function.RandomPasswordActivity;
import com.melon.unity.function.TimeSpaceCaptureActivity;
import com.melon.unity.function.money.MoneyActivity;
import com.melon.unity.function.password.PasswordActivity;
import com.melon.unity.function.web.MelonWebActivity;
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
        if (engine == 0) {
            url = Constant.URL_BAI_DU + mContent.getValue();
        } else {
            url = Constant.URL_BING + mContent.getValue();
        }

        boolean isSystemBrowser = PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("ieType", false);
        if (isSystemBrowser) {
            CommonUtil.enterBrowser(ctx, url);
        } else {
            enterMyWeb(ctx, url);
        }
    }

    /**
     * 进入自己的浏览器
     */
    private void enterMyWeb(Context ctx, String url) {
        Intent intent = new Intent(ctx, MelonWebActivity.class);
        intent.putExtra("url", url);
        ctx.startActivity(intent);
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
        return new String[]{"密码", "上海南站", "车墩站", "聊天", "天气", "金鸡", "沪深", "中国联通", "密码生成", "时空掠影", "舜宇", "升级"};
    }

    public void enterTag(int position, Context ctx) {
        switch (position) {
            case 0:
                //查询密码
                CommonUtil.enterActivity(ctx, PasswordActivity.class);
                break;
            case 1:
                //查询金山铁路-上海南站
                enterMyWeb(ctx, "http://www.shjstl.com/lately.php?station=%E4%B8%8A%E6%B5%B7%E5%8D%97%E7%AB%99");
                break;
            case 2:
                //查询金山铁路-车墩站
                enterMyWeb(ctx, "http://www.shjstl.com/lately.php?station=%E8%BD%A6%E5%A2%A9");
                break;
            case 3:
                //聊天
                enterMyWeb(ctx, API_BASE + API_TOPIC);
                break;
            case 4:
                //天气
                enterMyWeb(ctx, Constant.URL_BAI_DU + "天气");
                break;
            case 5:
                //金鸡
                CommonUtil.enterActivity(ctx, MoneyActivity.class);
                break;
            case 6:
                //沪深300
                enterMyWeb(ctx, Constant.URL_BAI_DU + "399300");
                break;
            case 7:
                //中国联通
                enterMyWeb(ctx, Constant.URL_BAI_DU + "600050");
                break;
            case 8:
                //随机密码
                CommonUtil.enterActivity(ctx, RandomPasswordActivity.class);
                break;
            case 9:
                //时空掠影
                CommonUtil.enterActivity(ctx, TimeSpaceCaptureActivity.class);
                break;
            case 10:
                //舜宇集团
                enterMyWeb(ctx, Constant.URL_BAI_DU + "02382");
                break;
            case 11:
                //升级
                CommonUtil.enterBrowser(ctx, API_BASE + APP_DOWNLOAD);
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