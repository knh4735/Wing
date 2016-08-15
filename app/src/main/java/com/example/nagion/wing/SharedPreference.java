package com.example.nagion.wing;

/**
 * Created by SeoKangYoun on 2016-08-15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference  extends Activity {
    private final String PREF_NAME = "com.rabiaband.pref";

        public final static String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
        public final static String PREF_MAIN_VALUE = "PREF_MAIN_VALUE";
        static Context mContext;

        public SharedPreference(Context c) {
            mContext = c;
        }

        public void put(String key, String value) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(key, value);
            editor.commit();
        }

        public void put(String key, boolean value) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean(key, value);
            editor.commit();
        }

        public void put(String key, int value) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putInt(key, value);
            editor.commit();
        }

        public String getValue(String key, String dftValue) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);

            try {
                return pref.getString(key, dftValue);
            } catch (Exception e) {
                return dftValue;
            }

        }

        public int getValue(String key, int dftValue) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);

            try {
                return pref.getInt(key, dftValue);
            } catch (Exception e) {
                return dftValue;
            }

        }

        public boolean getValue(String key, boolean dftValue) {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                    Activity.MODE_PRIVATE);

            try {
                return pref.getBoolean(key, dftValue);
            } catch (Exception e) {
                return dftValue;
            }
        }
}
