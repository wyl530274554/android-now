package com.melon.unity.function.settings;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.melon.commonlib.util.LogUtil;
import com.melon.commonlib.util.ToastUtil;
import com.melon.unity.R;

/**
 * V层
 * 负责界面显示及交互
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat switchOnlineState;
    SwitchPreferenceCompat switchIeTypeState;
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
        switchIeTypeState = findPreference("ieType");
        ListPreference engine = findPreference("engine");
        assert engine != null;
        int value = Integer.parseInt(engine.getValue());
        LogUtil.d("value: " + value);
    }
}
