package com.melon.unity.function.home;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
    private final String[] mTags;

    public HomeAdapter(String[] tags) {
        this.mTags = tags;
    }

    @Override
    public int getCount() {
        return mTags.length;
    }

    @Override
    public Object getItem(int position) {
        return mTags[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(parent.getContext());
        tv.setTextSize(20);
        tv.setHeight(150);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setGravity(Gravity.CENTER);
        tv.setText(mTags[position]);
        return tv;
    }
}
