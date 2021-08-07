package com.melon.unity.function.password;

import com.melon.commonlib.util.ApiUtil;
import com.melon.commonlib.util.HttpUtil;
import com.melon.commonlib.util.LogUtil;
import com.melon.unity.listener.NetCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PasswordModel {

    public void queryPasswords(String content, NetCallback<List<Password>> netCallback) {
        String url = ApiUtil.API_PASSWORD + content;
        HttpUtil.doGet(url, new HttpUtil.HttpCallbackStringListener() {
            @Override
            public void onFinish(String response) {
                List<Password> passwords = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        int id = jsonObject.optInt("id");
                        String title = jsonObject.optString("title");
                        String username = jsonObject.optString("username");
                        String pwd = jsonObject.optString("pwd");
                        String remark = jsonObject.optString("remark");
                        Password password = new Password(id, title, username, pwd, remark);
                        passwords.add(password);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.e("Json异常");
                }
                netCallback.onSuccess(passwords);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e("getPassword error: " + e.getMessage());
            }
        });
    }
}
