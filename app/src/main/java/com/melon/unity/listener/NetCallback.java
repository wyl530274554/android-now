package com.melon.unity.listener;

public interface NetCallback<T> {
    void onSuccess(T result);
    void onFail();
}
