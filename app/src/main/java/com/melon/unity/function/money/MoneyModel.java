package com.melon.unity.function.money;

import com.melon.commonlib.util.ApiUtil;
import com.melon.commonlib.util.DESUtil;
import com.melon.commonlib.util.HttpUtil;
import com.melon.commonlib.util.LogUtil;
import com.melon.unity.function.password.Password;
import com.melon.unity.listener.NetCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoneyModel {
    public void queryMoneys(NetCallback<List<Money>> callback) {
        String url = ApiUtil.API_MONEY + "/all";
        HttpUtil.doGet(url, new HttpUtil.HttpCallbackStringListener() {
            @Override
            public void onFinish(String response) {
                long start = System.currentTimeMillis();

                List<Money> moneyList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        int id = jsonObject.optInt("id");
                        String title = jsonObject.optString("title");
                        int level = jsonObject.optInt("level");
                        int amount = jsonObject.optInt("amount");
                        int ratio = jsonObject.optInt("ratio");
                        Money money = new Money(id, title,level,amount,ratio);
                        moneyList.add(money);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.e("Json异常");
                }

                LogUtil.d("共花费时间：" + (System.currentTimeMillis() - start));
                callback.onSuccess(moneyList);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e("getMoneyList error: " + e.getMessage());
            }
        });
    }
}
