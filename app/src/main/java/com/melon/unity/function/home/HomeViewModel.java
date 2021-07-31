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
}