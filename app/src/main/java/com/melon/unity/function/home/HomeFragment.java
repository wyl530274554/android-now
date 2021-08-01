package com.melon.unity.function.home;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.melon.commonlib.BaseFragment;
import com.melon.unity.R;
import com.melon.unity.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener {

    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mViewDataBinding;

    @Override
    protected void init() {
        mViewDataBinding.ivHomeSearch.setOnClickListener(this);
        mViewDataBinding.etHomeContent.setOnEditorActionListener(this);
        mViewDataBinding.ivHomeDel.setOnClickListener(this);

        mHomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewDataBinding.etHomeContent.setText(s);
            }
        });
        mViewDataBinding.gvHomeTag.setAdapter(new HomeAdapter(mHomeViewModel.getTags()));
        mViewDataBinding.gvHomeTag.setOnItemClickListener(this);
    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBinding = (FragmentHomeBinding) viewDataBinding;
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
            String content = mViewDataBinding.etHomeContent.getText().toString().trim();
            mHomeViewModel.search(getContext(), content);
        }

        if (v.getId() == R.id.iv_home_del) {
            mHomeViewModel.deleteSearchContent();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String content = v.getText().toString().trim();
            mHomeViewModel.search(getContext(), content);
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mHomeViewModel.enterTag(position, getContext());
    }
}