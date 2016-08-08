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

    private JSONObject returnObj;
    private String url = "10.10.49.217";

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
            Log.w("json","---------------------------------------"+request);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            try {
                final String strJsonOutput = response.body().string();
                final JSONObject jsonOutput = new JSONObject(strJsonOutput);
                setReturnObj(jsonOutput);
                Log.w("json","---------------------------------------"+jsonOutput);
            }
            catch (Exception e){e.printStackTrace();}
        }
    };

    public void getWing(String id) {

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(url)
                .port(8888)
                .addPathSegment("wingtest.php")
                .addQueryParameter("cmd", "getWing")// - get방식
                .addQueryParameter("from", id)
                .build();

        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(httpUrl)
               // .post(reqBody)
                .build();


        client.newCall(request).enqueue(callbackAfterGettingMessage);
    }
}
