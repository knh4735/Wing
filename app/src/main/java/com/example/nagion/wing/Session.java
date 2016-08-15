package com.example.nagion.wing;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.content.Context;
import android.view.Display;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nagion on 2016. 8. 13..
 */
public class Session {

    private static boolean isSetSession = false;

    private static String noAcnt = "";
    private static String idAcnt = "";
    private static String nickAcnt = "";
    private static String nameSi = "";
    private static String emailSi = "";
    private static String phoneSi = "";
    private static String token = "";
    private static String introSi = "";





    public static void setSession(JSONObject data, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            isSetSession = true;
            sharedPreferences.edit().putBoolean("isSetSession", true).apply();

            JSONObject sessionData = data.getJSONObject("result");

            noAcnt = sessionData.getString("no_acnt");
            sharedPreferences.edit().putString("no_acnt", noAcnt).apply();
            idAcnt = sessionData.getString("id_acnt");
            sharedPreferences.edit().putString("id_acnt", idAcnt).apply();
            nickAcnt = sessionData.getString("nick_acnt");
            sharedPreferences.edit().putString("nick_acnt", nickAcnt).apply();
            token = sessionData.getString("token");
            sharedPreferences.edit().putString("token", token).apply();

            JSONObject userInfo = sessionData.getJSONObject("info");

            nameSi = userInfo.getString("name_si");
            sharedPreferences.edit().putString("name_si", nameSi).apply();
            emailSi = userInfo.getString("email_si");
            sharedPreferences.edit().putString("email_si", emailSi).apply();
            phoneSi = userInfo.getString("phone_si");
            sharedPreferences.edit().putString("phone_si", phoneSi).apply();
            introSi = userInfo.getString("intro_si");
            sharedPreferences.edit().putString("intro_si", introSi).apply();
        }
        catch(Exception e){
            /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
        }
    }

    public static String getInstance(String target, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch(target){
            case "token" :
                String token = sharedPreferences.getString("token", ""); return token;
            case "noAcnt" : String noAcnt = sharedPreferences.getString("noAcnt", ""); return noAcnt;
            case "idAcnt" : String idAcnt = sharedPreferences.getString("idAcnt", ""); return idAcnt;
            case "nickAcnt" : String nickAcnt = sharedPreferences.getString("nickAcnt", ""); return nickAcnt;
            case "nameSi" : String nameSi = sharedPreferences.getString("nameSi", ""); return nameSi;
            case "emailSi" : String emailSi = sharedPreferences.getString("emailSi", ""); return emailSi;
            case "phoneSi" : String phoneSi = sharedPreferences.getString("phoneSi", ""); return phoneSi;
            case "introSi" : String introSi = sharedPreferences.getString("introSi", "");return introSi;
            default : return "";
        }
    }

    public static boolean isSet(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean("isSetSession", false);
    }

    public static void destroySession(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        noAcnt = "";
        idAcnt = "";
        nickAcnt = "";
        nameSi = "";
        emailSi = "";
        phoneSi = "";
        isSetSession = false;


        sharedPreferences.edit().putString("no_acnt", noAcnt).apply();
        sharedPreferences.edit().putString("id_acnt", idAcnt).apply();
        sharedPreferences.edit().putString("nick_acnt", nickAcnt).apply();
        sharedPreferences.edit().putString("token", token).apply();

        sharedPreferences.edit().putString("name_si", nameSi).apply();
        sharedPreferences.edit().putString("email_si", emailSi).apply();
        sharedPreferences.edit().putString("phone_si", phoneSi).apply();
        sharedPreferences.edit().putString("intro_si", introSi).apply();

        sharedPreferences.edit().putBoolean("isSetSession", false).apply();
    }
}
