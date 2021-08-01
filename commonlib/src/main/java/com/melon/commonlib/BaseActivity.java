package com.melon.commonlib;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Activity基类，方便统一控制
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Bundle savedInstanceState;
    protected ViewDataBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        getViewModel();
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        onDataBindingView(viewDataBinding);
        initView();
    }

    /**
     * 初始化viewModel
     */
    protected abstract void getViewModel();

    /**
     * 获取到了ViewDataBinding，回调回去，子Activity可以转为自己的具体类型
     */
    protected abstract void onDataBindingView(ViewDataBinding viewDataBinding);

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onClick(View v) {

    }
}
