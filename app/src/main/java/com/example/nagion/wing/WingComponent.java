package com.example.nagion.wing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class WingComponent extends LinearLayout {

    String noAcnt, name;
    int cnt;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.arg1 == 1){
                ((ViewGroup)getParent()).removeView(WingComponent.this);
            }
            else {
                Toast.makeText(getContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public WingComponent(Context context) {
        super(context);
    }

    public WingComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WingComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WingComponent(Context context, String noAcnt, String name, int cnt){
        super(context);
        this.noAcnt = noAcnt;
        this.name = name;
        this.cnt = cnt;
        init(name, cnt);
    }

    private void init(String name, int cnt) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.wing_component, this, false);
        addView(v);
        //TODO 커스텀윙

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView cntTv = (TextView) findViewById(R.id.cntTv);
        Button wingBtn = (Button) findViewById(R.id.wingBtn);

        nameTv.setText(name);
        cntTv.setText(String.valueOf(cnt));

        wingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpTask httpTask = new HttpTask();
                httpTask.wing(noAcnt, callbackWing);
            }
        });


    }

    private Callback callbackWing = new Callback() {
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
                e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
