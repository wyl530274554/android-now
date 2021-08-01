package com.melon.unity.function.password;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.melon.commonlib.util.CommonUtil;
import com.melon.unity.R;

import java.util.List;

public class PasswordAdapter extends BaseAdapter {
    private final List<Password> mPasswords;
    private int showPos;

    public PasswordAdapter(List<Password> passwords) {
        this.mPasswords = passwords;
    }

    @Override
    public int getCount() {
        return mPasswords.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        }

        TextView tv_password_title = convertView.findViewById(R.id.tv_password_title);
        TextView tv_password_user = convertView.findViewById(R.id.tv_password_user);
        TextView tv_password_pwd = convertView.findViewById(R.id.tv_password_pwd);
        TextView tv_password_other = convertView.findViewById(R.id.tv_password_other);

        //收起/展开
        if (showPos == position) {
            tv_password_user.setVisibility(View.VISIBLE);
            tv_password_pwd.setVisibility(View.VISIBLE);
            tv_password_other.setVisibility(View.VISIBLE);
        } else {
            tv_password_user.setVisibility(View.GONE);
            tv_password_pwd.setVisibility(View.GONE);
            tv_password_other.setVisibility(View.GONE);
        }

        Password password = mPasswords.get(position);
        tv_password_title.setText("(" + (++position) + ") " + password.getTitle());

        if (CommonUtil.isEmpty(password.getUsername())) {
            tv_password_user.setVisibility(View.GONE);
            tv_password_user.setText("");
        } else {
            tv_password_user.setText("账号：" + password.getUsername());
        }

        if (CommonUtil.isEmpty(password.getPwd())) {
            tv_password_pwd.setText("");
            tv_password_pwd.setVisibility(View.GONE);
        } else {
            tv_password_pwd.setText("密码：" + password.getPwd());
        }

        if (CommonUtil.isEmpty(password.getRemark())) {
            tv_password_other.setText("");
            tv_password_other.setVisibility(View.GONE);
        } else {
            tv_password_other.setText(password.getRemark());
        }

        return convertView;
    }

    public void show(int position) {
        showPos = position;
        notifyDataSetChanged();
    }
}
