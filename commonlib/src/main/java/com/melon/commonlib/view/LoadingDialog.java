package com.melon.commonlib.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.melon.commonlib.R;
import com.melon.commonlib.util.CommonUtil;

public class LoadingDialog extends ProgressDialog {
    private Context context;
    private String msg = "处理中...";

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialogStyle);
        this.context = context;
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        TextView tv = this.findViewById(R.id.tv);
        tv.setText(msg);
    }

    public void setShowMsg(String msg) {
        if (CommonUtil.isNotEmpty(msg)) {
            this.msg = msg;
        }
    }
}
