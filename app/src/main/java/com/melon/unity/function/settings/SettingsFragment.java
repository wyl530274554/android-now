package com.melon.unity.function.settings;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.melon.commonlib.util.LogUtil;
import com.melon.unity.R;

/**
 * V层
 * 负责界面显示及交互
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat switchOnlineState;
    SettingsViewModel mViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //获取viewModel实例
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        init();
    }

    private void init() {
        LogUtil.d("init: ");
        switchOnlineState = findPreference("online");

        //注册数据观察
        mViewModel.getOnlineState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOnline) {
                switchOnlineState.setChecked(isOnline);
            }
        });

        //查询状态
        mViewModel.initOnlineState();
    }
}
