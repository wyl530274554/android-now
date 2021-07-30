package com.melon.unity.function.settings;


import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.ViewDataBinding;

import com.melon.commonlib.BaseActivity;
import com.melon.unity.R;
import com.melon.unity.databinding.SettingsActivityBinding;

public class SettingsActivity extends BaseActivity {
    SettingsActivityBinding mViewDataBinding;

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBinding = (SettingsActivityBinding) viewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.settings_activity;
    }

    @Override
    protected void initView() {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("设置");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}