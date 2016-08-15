package com.example.nagion.wing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class VibeActivity extends AppCompatActivity {

    Button vibeBtn, finishBtn;

    String to;
    List<Long> ptrn = new ArrayList<Long>();

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.arg1 == 1){
                VibeActivity.this.finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);

        Intent intent = getIntent();
        to = intent.getStringExtra("to");

        vibeBtn = (Button) findViewById(R.id.vibe);
        finishBtn = (Button) findViewById(R.id.complete);

        vibeBtn.setOnTouchListener(new View.OnTouchListener() {

            GregorianCalendar gcDown = null, gcUp = null;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        gcDown = new GregorianCalendar();
                        Log.w("ButtonDown", "=----------------------------------" + gcDown.getTime().getTime());//(Calendar.ZONE_OFFSET));
                        break;
                    case MotionEvent.ACTION_UP :
                        if(gcDown != null) {
                            gcUp = new GregorianCalendar();
                            Log.w("ButtonUp", "=----------------------------------" + gcUp.getTime().getTime());

                            long interval = gcUp.getTime().getTime() - gcDown.getTime().getTime();

                            Log.w("TimeInterval", "=----------------------------------" + interval);

                            ptrn.add(interval);
                            ptrn.add(Long.parseLong("1000"));
                        }
                        break;
                }
                return false;
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ptrnString = "0";
                int i = 1;
                for (Long l : ptrn) {
                    ptrnString += "^"+String.valueOf(l);
                }

                ptrn = new ArrayList<Long>();

                HttpTask httpTask = new HttpTask();
                httpTask.customWing(Session.getInstance("noAcnt", getApplicationContext()), to, ptrnString, callbackCustomWing);
            }
        });
    }



    private Callback callbackCustomWing = new Callback() {
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
                if(result.equals("Success")){
                    msg.arg1 = 1;
                }
                else{
                    msg.arg1 = 0;
                }
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
