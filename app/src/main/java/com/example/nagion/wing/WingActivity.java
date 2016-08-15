package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WingActivity extends AppCompatActivity {

    EditText nameEt;
    JSONArray wingComponentList;
    Button makeBtn;
    LinearLayout rl;
    android.support.design.widget.FloatingActionButton Mn;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                Bundle data = msg.getData();
                String jsonString = data.getString("jsonString");
                JSONArray wingComponentList = new JSONArray(jsonString);
                Log.w("RETURN", "-------------------------------" + wingComponentList);

                for(int i=0;i<wingComponentList.length();i++){
                    JSONObject wingComponentObj = wingComponentList.getJSONObject(i);
                    String no = wingComponentObj.getString("no_acnt");
                    String name = wingComponentObj.getString("nick_acnt");
                    int cnt = wingComponentObj.getInt("cnt_wi");

                    WingComponent wc = new WingComponent(getApplicationContext(), no, name, cnt);
                    Log.w("RETURN", "-------------------------------" + wc);
                    rl.addView(wc);
                }
            }
            catch (Exception e){
                Log.e("E","error");
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        HttpTask httpTask = new HttpTask();
        httpTask.getWing(Session.getInstance("noAcnt", getApplicationContext()), callbackGetWing);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wing);

        if(!Session.isSet(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }



        rl = (LinearLayout) findViewById(R.id.wrapper);


        Mn = (android.support.design.widget.FloatingActionButton) findViewById(R.id.menu);
        Mn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });



        HttpTask httpTask = new HttpTask();
        httpTask.getWing(Session.getInstance("noAcnt", getApplicationContext()), callbackGetWing);


    }


    private Callback callbackGetWing = new Callback() {
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
                //e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
