package com.example.nagion.wing;

import android.util.Log;

import org.json.JSONObject;

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

    public static void setSession(JSONObject data){
        isSetSession = true;

        try {
            JSONObject sessionData = data.getJSONObject("result");

            noAcnt = sessionData.getString("no_acnt");
            idAcnt = sessionData.getString("id_acnt");
            nickAcnt = sessionData.getString("nick_acnt");

            JSONObject userInfo = sessionData.getJSONObject("info");

            nameSi = userInfo.getString("name_si");
            emailSi = userInfo.getString("email_si");
            phoneSi = userInfo.getString("phone_si");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getInstance(String target){
        switch(target){
            case "noAcnt" : return noAcnt;
            case "idAcnt" : return idAcnt;
            case "nickAcnt" : return nickAcnt;
            case "nameSi" : return nameSi;
            case "emailSi" : return emailSi;
            case "phoneSi" : return phoneSi;
            default : return "";
        }
    }

    public static boolean isSet(){
        return isSetSession;
    }

    public static void destroySession(){
        noAcnt = "";
        idAcnt = "";
        nickAcnt = "";
        nameSi = "";
        emailSi = "";
        phoneSi = "";
        isSetSession = false;
    }

}
