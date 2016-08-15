package com.example.nagion.wing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class NoticeActivity extends AppCompatActivity {
    LinearLayout rl;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                Bundle data = msg.getData();
                String jsonString = data.getString("jsonString");
                JSONArray noticeComponentList = new JSONArray(jsonString);
                Log.w("RETURN", "-------------------------------" + noticeComponentList);


                for(int i=0;i<noticeComponentList.length();i++){
                    JSONObject noticeComponentObj = noticeComponentList.getJSONObject(i);

                    String title = noticeComponentObj.getString("title_ntc");
                    String content = noticeComponentObj.getString("content_ntc");

                    NoticeComponent nc = new NoticeComponent(getApplicationContext(), title, content);
                    Log.w("Component", "-------------------------------" + nc);
                    rl.addView(nc);
                }

            }catch (Exception e){}

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        rl = (LinearLayout) findViewById(R.id.wrapper);

        HttpTask httpTask = new HttpTask();
        httpTask.getNotice(callbackGetNotice);
    }


    private Callback callbackGetNotice = new Callback() {
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

                Message msg = handler.obtainMessage();
                Bundle data = new Bundle();
                data.putString("jsonString", result);
                msg.setData(data);
                handler.sendMessage(msg);

            }
            catch (Exception e){
                // before code
                e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
