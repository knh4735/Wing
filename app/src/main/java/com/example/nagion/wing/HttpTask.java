package com.example.nagion.wing;


import android.util.Log;

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

    private OkHttpClient client = new OkHttpClient();
    private JSONObject returnObj;

    public static String hostUrl = "192.168.200.172";

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
            e.printStackTrace();
            Log.w("fail","---------------------------------------"+request);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            try {
                final String strJsonOutput = response.body().string();
                Log.w("json","---------------------------------------"+strJsonOutput);
                final JSONObject jsonOutput = new JSONObject(strJsonOutput);
                setReturnObj(jsonOutput);
                Log.w("json","---------------------------------------"+jsonOutput);
            }
            catch (Exception e){e.printStackTrace();}
        }
    };

    public void getWing(String id) {



        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
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

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }


    public void searchFriend(String idFriend) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("target", idFriend);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
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

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }

    public void getRank(String id) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
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

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }


    public void getNotice() {
        JSONObject jsonInput = new JSONObject();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
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

        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }

    public void wing(String id) {
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(hostUrl)
                .port(8888)
                .addPathSegment("wing.php")
                .addQueryParameter("cmd", "wing")// - get방식
                .addQueryParameter("acnt", id)
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
                .scheme("http")
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
}
