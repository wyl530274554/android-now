package com.melon.unity.function.password;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.melon.commonlib.BaseActivity;
import com.melon.commonlib.util.CommonUtil;
import com.melon.unity.R;
import com.melon.unity.databinding.ActivityPasswordBinding;

import java.util.List;

/**
 * 密码管理
 */
public class PasswordActivity extends BaseActivity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ActivityPasswordBinding mViewBinding;
    private PasswordViewModel mViewModel;
    private PasswordAdapter mPasswordAdapter;

    /**
     * 密码监视器
     */
    private class PasswordObserver implements Observer<List<Password>> {

        @Override
        public void onChanged(List<Password> passwords) {
            mPasswordAdapter = new PasswordAdapter(passwords);
            mViewBinding.lvPassword.setAdapter(mPasswordAdapter);
        }
    }

    @Override
    protected void getViewModel() {
        mViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        mViewModel.getPasswords().observe(this, new PasswordObserver());
    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewBinding = (ActivityPasswordBinding) viewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected void initView() {
        mViewBinding.etPwdSearch.setOnEditorActionListener(this);
        mViewBinding.lvPassword.setOnItemClickListener(this);
        mViewBinding.lvPassword.setOnItemLongClickListener(this);
        mViewBinding.btPasswordSubmit.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("密码");
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //点击搜索要做的操作
            String trim = textView.getText().toString().trim();
            mViewModel.queryPasswords(getApplicationContext(), trim);
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPasswordAdapter.show(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }

        if (item.getItemId() == R.id.action_add_pwd) {
            mViewBinding.rlPasswordAdd.setVisibility(View.VISIBLE);
            return true;
        }

        if (item.getItemId() == R.id.action_update_pwd) {
            // 更新密码库
            mViewModel.updateAllPwd(getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String pwd = mViewModel.getPasswords().getValue().get(position).getPwd();
        CommonUtil.copy(this, pwd);
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.bt_password_submit) {
            submitPassword();
            return;
        }
    }

    private void submitPassword() {
        String title = mViewBinding.etPasswordTitle.getText().toString().trim();
        String pwd = mViewBinding.etPasswordPwd.getText().toString().trim();
        String username = mViewBinding.etPasswordUsername.getText().toString().trim();
        String remark = mViewBinding.etPasswordRemark.getText().toString().trim();
        Password password = new Password(title, username, pwd, remark);
        mViewModel.insertPassword(password);
    }
}
