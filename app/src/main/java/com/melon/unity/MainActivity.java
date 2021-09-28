package com.melon.unity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.melon.commonlib.BaseActivity;
import com.melon.commonlib.util.CommonUtil;
import com.melon.unity.databinding.ActivityMainBinding;
import com.melon.unity.function.settings.SettingsActivity;
import com.melon.unity.net.SocketClient;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding mViewDataBing;
    private TextView mTitleView;

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
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(mViewDataBing.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mViewDataBing.navView, navController);

        //显示版本
        TextView subTitleView = mViewDataBing.navView.getHeaderView(0).findViewById(R.id.tv_nav_header_subtitle);
        mTitleView = mViewDataBing.navView.getHeaderView(0).findViewById(R.id.tv_nav_header_title);
        subTitleView.setText("V".concat(CommonUtil.getVersion(getApplicationContext())));

        showBackHome();
    }

    private void showBackHome() {
        AppApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTitleView.setText(SocketClient.getInstance().isOnline() ? "Netty在线" : "Netty不在线");
            }
        }, 5000);
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

}