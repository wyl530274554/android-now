package com.melon.unity.function.password;

import android.view.KeyEvent;
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

    @Override
    protected void getViewModel() {
        mViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        mViewModel.getPasswords().observe(this, new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {
                //FIXME 不要每次new一个adapter
                mPasswordAdapter = new PasswordAdapter(passwords);
                mViewBinding.lvPassword.setAdapter(mPasswordAdapter);
            }
        });
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
            mViewModel.queryPasswords(trim);
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPasswordAdapter.show(position);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String pwd = mViewModel.getPasswords().getValue().get(position).getPwd();
        CommonUtil.copy(this, pwd);
        return true;
    }
}
