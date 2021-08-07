package com.melon.unity.function.home;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.melon.commonlib.BaseFragment;
import com.melon.commonlib.util.LogUtil;
import com.melon.unity.R;
import com.melon.unity.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, TextWatcher {

    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mViewDataBinding;

    @Override
    protected void init() {
        mViewDataBinding.ivHomeSearch.setOnClickListener(this);
        mViewDataBinding.etHomeContent.setOnEditorActionListener(this);
        mViewDataBinding.etHomeContent.addTextChangedListener(this);
        mViewDataBinding.ivHomeDel.setOnClickListener(this);

        mViewDataBinding.gvHomeTag.setAdapter(new HomeAdapter(mHomeViewModel.getTags()));
        mViewDataBinding.gvHomeTag.setOnItemClickListener(this);
    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBinding = (FragmentHomeBinding) viewDataBinding;
        mViewDataBinding.setHomeViewModel(mHomeViewModel);
        mViewDataBinding.setLifecycleOwner(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void getViewModel() {
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.iv_home_search) {
            mHomeViewModel.search(getContext());
        }

        if (v.getId() == R.id.iv_home_del) {
            mHomeViewModel.deleteSearchContent();
            LogUtil.d("删除");
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mHomeViewModel.search(getContext());
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mHomeViewModel.enterTag(position, getContext());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //双向绑定的一部分（View->ViewModel）(可以在xml中配置方法调用，用来设置value值)，另一部分是xml中的android:text="@{homeViewModel.content}" （ViewModel->View）
        mHomeViewModel.getContent().setValue(s.toString().trim());
    }
}