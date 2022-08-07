package com.melon.unity.function;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.melon.commonlib.BaseActivity;
import com.melon.commonlib.util.CommonUtil;
import com.melon.unity.R;
import com.melon.unity.databinding.ActivityRandomPasswordBinding;

import java.util.Random;

public class RandomPasswordActivity extends BaseActivity {
    ActivityRandomPasswordBinding mViewDataBing;

    @Override
    protected void getViewModel() {

    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBing = (ActivityRandomPasswordBinding) viewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_random_password;
    }

    @Override
    protected void initView() {
        mViewDataBing.btRandomPasswordOk.setOnClickListener(this);
        mViewDataBing.tvRandomPasswordResult.setOnClickListener(this);
    }

    String createPassword(int num) {
        String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+{}|<>?";
        Random random = new Random();
        char[] cs = new char[num];
        for (int i = 0; i < num; i++) {
            cs[i] = baseStr.charAt(random.nextInt(81));
        }
        return String.valueOf(cs);
    }

    String pwd;

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.bt_random_password_ok) {
            String num = mViewDataBing.etRandomPasswordNum.getText().toString().trim();
            if (TextUtils.isEmpty(num)) {
                return;
            }
            pwd = createPassword(Integer.parseInt(num));
            mViewDataBing.tvRandomPasswordResult.setText(pwd);
        }

        if (v.getId() == R.id.tv_random_password_result) {
            if (TextUtils.isEmpty(pwd)) {
                return;
            }
            CommonUtil.copy(this, pwd);
        }
    }
}
