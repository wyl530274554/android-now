package com.melon.commonlib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getViewModel();
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        onDataBindingView(viewDataBinding);
        init();
        return viewDataBinding.getRoot();
    }

    /**
     * 初始化View
     */
    protected abstract void init();

    /**
     * 获取到了ViewDataBinding，回调回去，子类可以转为自己的具体类型
     */
    protected abstract void onDataBindingView(ViewDataBinding viewDataBinding);

    /**
     * Fragment布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化viewModel
     */
    protected abstract void getViewModel();

    @Override
    public void onClick(View v) {

    }
}
