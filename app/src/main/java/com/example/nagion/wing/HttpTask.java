package com.example.nagion.wing;


import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
/**
 * Created by Nagion on 2016. 8. 2..
 */
public class HttpTask {


    private String http = "http";


    private OkHttpClient client = new OkHttpClient();
    private JSONObject returnObj;

    public static String hostUrl = "192.168.0.104";

    public JSONObject getReturnObj(){
        return returnObj;
    }

    public void setReturnObj(JSONObject json){
        returnObj = json;
        return;
    }

    private Callback callbackAfterGettingMessage = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
            Log.w("fail","---------------------------------------"+request);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            final String strJsonOutput = response.body().string();
            Log.w("String","---------------------------------------"+strJsonOutput);

            try {
                final JSONObject jsonOutput = new JSONObject(strJsonOutput);
                setReturnObj(jsonOutput);
                Log.w("JSON","---------------------------------------"+jsonOutput);
            }
            catch (Exception e){
                 /* before code
                e.printStackTrace();
                */
                Log.e("e","error occured");
            }
        }
    };

    public void getWing(String id, Callback callback) {



        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
            /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");

        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "getWing")// - get방식
                .addQueryParameter("from", id)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void searchFriend(String idFriend, Callback callback) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("target", idFriend);
        } catch (Exception e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "searchFriend")// - get방식
                .addQueryParameter("target", idFriend)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void getRank(String id, Callback callback) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "getRank")// - get방식
                .addQueryParameter("id", id)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void getNotice(Callback callback) {
        JSONObject jsonInput = new JSONObject();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "getNotice")// - get방식
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void wing(String id) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "wing")// - get방식
                .addQueryParameter("from", Session.getInstance("noAcnt"))
                .addQueryParameter("to", id)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }

    public void logout(String acnt, String token) {


        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "logout")// - get방식
                .addQueryParameter("acnt", acnt)
                .addQueryParameter("token", token)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }

    public void requestFriend(String to, String msg, Callback callback){

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "requestFriend")// - get방식
                .addQueryParameter("from", Session.getInstance("noAcnt"))
                .addQueryParameter("to", to)
                .addQueryParameter("msg", msg)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void signUp(String id, String pw, String nick, String name, String email, String phone, String intro, Callback callbackSignUp) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "signUp")// - get방식
                .addQueryParameter("id", id)
                .addQueryParameter("pw", pw)
                .addQueryParameter("nick", nick)
                .addQueryParameter("name", name)
                .addQueryParameter("phone", phone)
                .addQueryParameter("email", email)
                .addQueryParameter("intro", intro)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callbackSignUp);
    }

    public void duplicatedId(String id, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "duplicatedId")// - get방식
                .addQueryParameter("id", id)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void reqConfirm(String flag, String from, String to, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "reqConfirm")// - get방식
                .addQueryParameter("flag", flag)
                .addQueryParameter("from", from)
                .addQueryParameter("to", to)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void getRequest(String noAcnt, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "getRequest")// - get방식
                .addQueryParameter("acnt", noAcnt)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void checkPw(String noAcnt, String pw, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "checkPw")// - get방식
                .addQueryParameter("acnt", noAcnt)
                .addQueryParameter("pw", pw)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void unregister(String noAcnt, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "unregister")// - get방식
                .addQueryParameter("acnt", noAcnt)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void changePw(String noAcnt, String bfPw, String pw, Callback callback) {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "changePw")// - get방식
                .addQueryParameter("acnt", noAcnt)
                .addQueryParameter("bfPw", bfPw)
                .addQueryParameter("pw", pw)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }


    public void changeInfo(String pw, String nick, String name, String phone, String email, String intro, String nameCk, String phoneCk, String emailCk, String introCk, Callback callback) {

        //param : "changeInfo", pw, nick, name, phone, email, intro, nameCk, phoneCk, emailCk, introCk

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(http)
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "changePw")// - get방식
                .addQueryParameter("acnt", Session.getInstance("noAcnt"))
                .addQueryParameter("pw", pw)
                .addQueryParameter("nick", nick)
                .addQueryParameter("name", name)
                .addQueryParameter("phone", phone)
                .addQueryParameter("email", email)
                .addQueryParameter("intro", intro)
                .addQueryParameter("nameCk", nameCk)
                .addQueryParameter("phoneCk", phoneCk)
                .addQueryParameter("emailCk", emailCk)
                .addQueryParameter("introCk", introCk)
                .build();
/*
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );*/

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();
        Log.w("request","-----------------------------------"+request);

        client.newCall(request).enqueue(callback);
    }

    public void login(String id, String pw, String token, Callback callback) {

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", token);
        } catch (Exception e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(HttpTask.hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "login")// - get방식
                .addQueryParameter("id", id)
                .addQueryParameter("pw", pw)
                .addQueryParameter("token", token)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
                //.post(reqBody)
                .build();

        Log.w("request", "---------------------------------------------"+request);


        client.newCall(request).enqueue(callback);
    }
}
