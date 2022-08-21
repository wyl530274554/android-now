package com.melon.unity.function;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.google.common.util.concurrent.ListenableFuture;
import com.melon.commonlib.BaseActivity;
import com.melon.commonlib.util.LogUtil;
import com.melon.commonlib.util.ToastUtil;
import com.melon.unity.R;
import com.melon.unity.databinding.ActivityTimeSpaceBinding;

import java.io.File;

public class TimeSpaceCaptureActivity extends BaseActivity {
    ActivityTimeSpaceBinding mViewDataBinding;
    /**
     * 要预览的相机
     */
    private static final CameraSelector MY_CAMERA = CameraSelector.DEFAULT_BACK_CAMERA;
    /**
     * 预览视图
     */
    private PreviewView mPreviewView;

    /**
     * 拍照
     */
    private ImageCapture mImageCapture;

    @Override
    protected void getViewModel() {

    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {
        mViewDataBinding = (ActivityTimeSpaceBinding) viewDataBinding;

        mPreviewView = mViewDataBinding.pvTimeSpace;
        mViewDataBinding.btTimeSpaceOk.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_time_space;
    }

    @Override
    protected void initView() {
        startCamera();
    }

    /**
     * 是否有相机使用权限
     */
    private boolean isCameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void startCamera() {
        //权限检测
        if (!isCameraPermissionGranted()) {
            LogUtil.e("没有摄像头权限");
            //权限申请
            return;
        }
        LogUtil.d("有了摄像头权限");


        //实现预览
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    //预览用例初始化
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());

                    //拍照用例初始化
                    mImageCapture = new ImageCapture.Builder().build();

                    cameraProvider.unbindAll();
                    cameraProvider.bindToLifecycle(TimeSpaceCaptureActivity.this, MY_CAMERA, preview, mImageCapture);
                } catch (Exception e) {
                    LogUtil.e("error: " + e.getMessage());
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_time_space_ok) {
            takePhoto();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        File photoFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        LogUtil.d("photoFile: " + photoFile.getPath());

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();
        mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                //注意这个Uri取不到
                LogUtil.d("onImageSaved: " + outputFileResults.getSavedUri());
                ToastUtil.toast("OK");
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                LogUtil.e("takePhoto onError");
            }
        });
    }
}
