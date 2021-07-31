package com.melon.unity.function.home;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.commonlib.util.CommonUtil;
import com.melon.commonlib.util.Constant;
import com.melon.commonlib.util.LogUtil;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    /**
     * 浏览器搜索
     */
    public void search(Context ctx, String content) {
        if (TextUtils.isEmpty(content)) {
            LogUtil.d("输入为空");
            return;
        }
        String url = Constant.URL_BAI_DU + content;
        CommonUtil.enterBrowser(ctx, url);
    }

    /**
     * 清空输入
     */
    public void deleteSearchContent() {
        mText.setValue("");
    }

    /**
     * 标签
     */
    public String[] getTags() {
        return new String[]{"密码", "上海南站", "车墩站", "聊天", "电话本"};
    }

    public void clickTag(int position, Context ctx) {
        switch (position) {
            case 0:
                //查询密码
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
}