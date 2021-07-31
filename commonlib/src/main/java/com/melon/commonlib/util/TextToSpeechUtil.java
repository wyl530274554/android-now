package com.melon.commonlib.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;

public class TextToSpeechUtil {
    private static TextToSpeech mSpeech;
    public static boolean isInitOk;
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    public static void init(Context ctx) {
        mSpeech = new TextToSpeech(ctx, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                LogUtil.d("onInit: " + status);
                isInitOk = (status == 0);
            }
        });
    }

    public static void playVoice(final String text) {
        if (!isInitOk) {
            LogUtil.d("TextToSpeech is not ready");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playVoice(text);
                }
            }, 2000);
            return;
        }

        mSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, text);
    }
}
