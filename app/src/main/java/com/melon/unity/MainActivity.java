package com.melon.unity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.melon.commonlib.BaseActivity;
import com.melon.commonlib.util.CommonUtil;
import com.melon.unity.databinding.ActivityMainBinding;
import com.melon.unity.function.settings.SettingsActivity;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding mViewDataBing;

    @Override
    protected void getViewModel() {
        //nothing
    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBing) {
        mViewDataBing = (ActivityMainBinding) viewDataBing;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mViewDataBing.includeMainBar.toolbar);
        mViewDataBing.includeMainBar.fab.setOnClickListener(this);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(mViewDataBing.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mViewDataBing.navView, navController);

        TextView subTimeView = mViewDataBing.navView.getHeaderView(0).findViewById(R.id.tv_nav_header_subtitle);
        subTimeView.setText("V" + CommonUtil.packageVersion(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            CommonUtil.enterActivity(this, SettingsActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}