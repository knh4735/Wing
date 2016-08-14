package com.example.nagion.wing;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
           // LoginTask lt = new LoginTask();
           // lt.execute("login", "1", token);
            sendRegistrationToServer(id, pw, token);
            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean("sentTokenToServer", true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean("sentTokenToServer", false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.

    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String id, String pw, String token) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("id", token);
        } catch (Exception e) {
            e.printStackTrace();
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


        client.newCall(request).enqueue(callbackAfterGettingMessage);
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
                Intent registrationComplete = new Intent("registrationComplete");
                LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(registrationComplete);

                final JSONObject jsonOutput = new JSONObject(strJsonOutput);
                Log.w("json","---------------------------------------"+jsonOutput);

                Session.setSession(jsonOutput);


                Intent i = new Intent(getApplicationContext(), WingActivity.class);
                startActivity(i);


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putBoolean("sentTokenToServer", true).apply();

            }
            catch (Exception e){e.printStackTrace();}
        }
    };
    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

    public class LoginTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        LoginTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("login")){
               // httpTask.login(params[1],params[2]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray jsonArray = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + jsonArray);



            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
