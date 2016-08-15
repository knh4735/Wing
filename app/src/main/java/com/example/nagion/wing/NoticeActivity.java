package com.example.nagion.wing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class NoticeActivity extends AppCompatActivity {
    LinearLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        rl = (LinearLayout) findViewById(R.id.wrapper);



        NoticeTask rt = new NoticeTask();
        rt.execute("getNotice");
    }

    public class NoticeTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        NoticeTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("getNotice")){
                httpTask.getNotice();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray noticeComponentList = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + noticeComponentList);


                for(int i=0;i<noticeComponentList.length();i++){
                    JSONObject noticeComponentObj = noticeComponentList.getJSONObject(i);

                    String title = noticeComponentObj.getString("title_ntc");
                    String content = noticeComponentObj.getString("content_ntc");

                    NoticeComponent nc = new NoticeComponent(getApplicationContext(), title, content);
                    Log.w("Component", "-------------------------------" + nc);
                    rl.addView(nc);
                }

            }catch (Exception e){
                 /* before code
                e.printStackTrace();
                */
                Log.e("e","error occured");
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
