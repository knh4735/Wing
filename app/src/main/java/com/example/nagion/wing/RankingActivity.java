package com.example.nagion.wing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class RankingActivity extends AppCompatActivity {
    LinearLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rl = (LinearLayout) findViewById(R.id.wrapper);

        RankingTask rt = new RankingTask();
        rt.execute("getRank", "1");
    }

    public class RankingTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        RankingTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("getRank")){
                httpTask.getRank(params[1]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray rankingComponentList = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + rankingComponentList);


                for(int i=0;i<rankingComponentList.length();i++){
                    JSONObject rankingComponentObj = rankingComponentList.getJSONObject(i);

                    String name = rankingComponentObj.getString("nick_acnt");
                    int cnt = rankingComponentObj.getInt("cnt_wi");

                    RankingComponent rc = new RankingComponent(getApplicationContext(), i+1, name, cnt);
                    Log.w("Component", "-------------------------------" + rc);
                    rl.addView(rc);
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
