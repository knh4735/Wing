package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WingActivity extends AppCompatActivity {

    EditText nameEt;
    JSONArray wingComponentList;
    Button makeBtn;
    LinearLayout rl;
    android.support.design.widget.FloatingActionButton Mn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wing);

        nameEt = (EditText) findViewById(R.id.nameEt);
        makeBtn = (Button) findViewById(R.id.makeBtn);
        rl = (LinearLayout) findViewById(R.id.wrapper);


        Mn = (android.support.design.widget.FloatingActionButton) findViewById(R.id.menu);
        Mn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        WingTask wt = new WingTask("1");
        wt.execute("getWing");

        Button vibe = (Button) findViewById(R.id.vibe);
        vibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VibeActivity.class);
                startActivity(intent);
            }
        });

        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = nameEt.getText().toString();
                if(tmp != null && tmp != ""){

                    WingComponent wc = new WingComponent(getApplicationContext(), tmp, 0);
                    rl.addView(wc);

                    nameEt.setText("");
                }
            }
        });


    }

    public class WingTask extends AsyncTask<String, Void, Void> {

        private final String noAcnt;
        private final HttpTask httpTask;

        WingTask(String noAcnt) {
            this.noAcnt = noAcnt;
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("getWing")){
                httpTask.getWing(noAcnt);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray wingComponentList = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + wingComponentList);


                for(int i=0;i<wingComponentList.length();i++){
                    JSONObject wingComponentObj = wingComponentList.getJSONObject(i);
                    String name = wingComponentObj.getString("nick_acnt");
                    int cnt = wingComponentObj.getInt("cnt_wi");

                    WingComponent wc = new WingComponent(getApplicationContext(), name, cnt);
                    Log.w("RETURN", "-------------------------------" + wc);
                    rl.addView(wc);
                }

            }catch (Exception e){}
        }

        @Override
        protected void onCancelled() {
        }
    }
}
