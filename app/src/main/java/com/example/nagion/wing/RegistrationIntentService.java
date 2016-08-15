package com.example.nagion.wing;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                Toast.makeText(RegistrationIntentService.this, "잘못된 아이디 혹은 비밀번호입니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

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

            // LoginTask lt = new LoginTask();
            // lt.execute("login", "1", token);
            HttpTask httpTask = new HttpTask();
            httpTask.login(id, pw, token, callbackSignUp);
            //     sendRegistrationToServer(id, pw, token);
            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
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
    private Callback callbackSignUp = new Callback() {
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
                Log.w("JSON","---------------------------------------"+jsonOutput);

                String result = jsonOutput.getString("result");
                Log.w("RESULT", "------------------------"+result);

                if(result.equals("Wrong")) {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 0;
                    handler.sendMessage(msg);
                }
                else {
                    Session.setSession(jsonOutput);

                    Intent registrationComplete = new Intent("registrationComplete");
                    LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(registrationComplete);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putBoolean("sentTokenToServer", true).apply();
                }
            }
            catch (Exception e){
                // before code
                e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
