package com.example.nagion.wing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConfirmActivity extends AppCompatActivity {

    LinearLayout wrapper = (LinearLayout) findViewById(R.id.wrapper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);


        GetReqTask grt = new GetReqTask();
        grt.execute("getRequest");

    }

    public static void removeComponent(ConfirmComponent cc){

    }

    public class GetReqTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        GetReqTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("getRequest")){
                httpTask.getRequest(Session.getInstance("noAcnt"));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray confirmComponentList = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + confirmComponentList);


                for(int i=0;i<confirmComponentList.length();i++){
                    JSONObject confirmComponentObj = confirmComponentList.getJSONObject(i);

                    String noAcnt = confirmComponentObj.getString("from_fr"),
                            name = confirmComponentObj.getString("nick_acnt"),
                            msg = confirmComponentObj.getString("msg_fr");

                    ConfirmComponent cc = new ConfirmComponent(getApplicationContext(), noAcnt, name, msg);
                    Log.w("Component", "-------------------------------" + cc);
                    wrapper.addView(cc);
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
