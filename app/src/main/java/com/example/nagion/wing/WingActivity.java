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

import org.json.JSONObject;

import java.util.ArrayList;

public class WingActivity extends AppCompatActivity {

    EditText nameEt;
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

                    WingComponent wc = new WingComponent(getApplicationContext(), tmp);
                    rl.addView(wc);

                    nameEt.setText("");
                }
            }
        });


    }

    public class WingTask extends AsyncTask<String, Void, JSONObject> {

        private final String noAcnt;
        private final HttpTask httpTask;

        WingTask(String noAcnt) {
            this.noAcnt = noAcnt;
            httpTask = new HttpTask();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            if(params[0].equals("getWing")){
                httpTask.getWing(noAcnt);
                JSONObject rtn = httpTask.getReturnObj();
                Log.w("RETURN", "-------------------------------"+rtn);

                return rtn;
            }

            return null;
        }

        @Override
        protected void onPostExecute(final JSONObject success) {
        }

        @Override
        protected void onCancelled() {
        }
    }
}
