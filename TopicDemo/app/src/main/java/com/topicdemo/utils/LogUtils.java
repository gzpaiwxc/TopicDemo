package com.topicdemo.utils;

import android.util.Log;

/**
 * Created by THL on 2017/4/24.
 */

public class LogUtils {
    private static final String TAG = "topicdemo";
    private static boolean isTest = true;

    public static void d(String text) {
        if (isTest) {
            Log.d(TAG, text);
        }
    }

    public static void e(String text) {
        if (isTest) {
            Log.e(TAG, text);
        }
    }

    public static void i(String text) {
        if (isTest) {
            Log.i(TAG, text);
        }
    }
}
