package com.example.SDK;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bruce-too
 * on 2014/10/15
 * Time 15:40.
 */
public class SharePreUtils {

    public static SharedPreferences preferences;
    public static String PreName = "CONFIG";

    public static void saveFLoat(Context ct, String key, float value) {
        if (preferences == null) {
            preferences = ct.getSharedPreferences(PreName, ct.MODE_PRIVATE);
        }
        preferences.edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context ct,String key) {
        if (preferences == null) {
            preferences = ct.getSharedPreferences(PreName, ct.MODE_PRIVATE);
        }
        return preferences.getFloat(key, 0);
    }
}
