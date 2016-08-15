package com.example.nagion.wing;

/**
 * Created by SeoKangYoun on 2016-08-15.
 */

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("static-access")
public class SharedPreference {


    public static void setPreferences(Context context, String key, String value) {
        SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getPreferences(Context context, String key) {
        SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return p.getString(key, "");
    }
}
