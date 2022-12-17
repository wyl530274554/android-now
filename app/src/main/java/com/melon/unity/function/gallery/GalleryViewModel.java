package com.melon.unity.function.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Properties;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is gallery fragment");
        Properties properties = System.getProperties();
        StringBuilder sb = new StringBuilder();
        sb.append("total size: "+properties.stringPropertyNames().size()+"\n\n");

        for(String key:properties.stringPropertyNames()){
            sb.append("key: "+ key+"\n");
            String val = properties.getProperty(key);
            sb.append("val: "+val+"\n\n");
        }
        mText.setValue(sb.toString());
    }

    public LiveData<String> getText() {
        return mText;
    }
}