package com.melon.unity.function.password;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.unity.listener.NetCallback;

import java.util.List;

/**
 * ViewModel存储和管理 UI 相关数据
 */
public class PasswordViewModel extends ViewModel {
    private final MutableLiveData<List<Password>> mPasswords = new MutableLiveData<>();
    private final PasswordModel mModel = new PasswordModel();

    public MutableLiveData<List<Password>> getPasswords() {
        return mPasswords;
    }

    public void queryPasswords(Context ctx, String key) {
        mModel.queryPasswords(ctx, key, new NetCallback<List<Password>>() {
            @Override
            public void onSuccess(List<Password> passwords) {
                //子线程中不能用setValue，要用postValue
                mPasswords.postValue(passwords);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void updateAllPwd(Context ctx){
        mModel.updateAllPwd(ctx);
    }
    public void insertPassword(Password password){
        mModel.insert(password);
    }
}
