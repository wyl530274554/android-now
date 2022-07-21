package com.melon.unity.function.money;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.melon.commonlib.BaseActivity;
import com.melon.unity.R;
import com.melon.unity.databinding.ActivityMoneyBinding;

import java.util.List;

public class MoneyActivity extends BaseActivity {
    ActivityMoneyBinding mViewDataBinding;
    private MoneyViewModel mViewModel;

    /**
     * 监视器
     */
    private class MoneyObserver implements Observer<List<Money>> {

        @Override
        public void onChanged(List<Money> moneyList) {
            String str = "共有：" + moneyList.size() + "只鸡\n\n";
            for (int i = 0; i < moneyList.size(); i++) {
                Money money = moneyList.get(i);
                str += money.title + " : " + money.amount + " : " + money.ratio +"\n\n";
            }

            mViewDataBinding.tvMoneyData.setText(str);
        }
    }

    @Override
    protected void getViewModel() {
        mViewModel = new ViewModelProvider(this).get(MoneyViewModel.class);
        mViewModel.getMoneys().observe(this, new MoneyObserver());
    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBinding = (ActivityMoneyBinding) viewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money;
    }

    @Override
    protected void initView() {
        mViewModel.queryMoneys();
    }
}
