package com.melon.unity.function.money;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.melon.unity.listener.NetCallback;

import java.util.List;

public class MoneyViewModel extends ViewModel {
    private final MutableLiveData<List<Money>> mMoneys = new MutableLiveData<>();
    private final MoneyModel mModel = new MoneyModel();

    public MutableLiveData<List<Money>> getMoneys() {
        return mMoneys;
    }

    public void queryMoneys() {
        mModel.queryMoneys(new NetCallback<List<Money>>() {
            @Override
            public void onSuccess(List<Money> moneyList) {
                //子线程中不能用setValue，要用postValue
                mMoneys.postValue(moneyList);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
