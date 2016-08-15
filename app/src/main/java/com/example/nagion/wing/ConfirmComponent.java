package com.example.nagion.wing;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Nagion on 2016. 8. 14..
 */
public class ConfirmComponent  extends LinearLayout {

    String noAcnt;
    String name, msg;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                if(msg.arg1 == 1) {
                    Toast.makeText(getContext(), "친구가 되었습니다!", Toast.LENGTH_SHORT).show();
                    //TODO ConfirmActivity.removeComponent(ConfirmComponent.this);
                }
                else if(msg.arg1 == 2){
                    Toast.makeText(getContext(), "거절하였습니다.", Toast.LENGTH_SHORT).show();
                    //TODO ConfirmActivity.removeComponent(ConfirmComponent.this);
                }
                else {
                    Toast.makeText(getContext(), "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){}

        }
    };



    public ConfirmComponent(Context context) {
        super(context);
    }

    public ConfirmComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConfirmComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ConfirmComponent(Context context, String noAcnt, String name, String msg){
        super(context);

        this.noAcnt = noAcnt;
        this.name = name;
        this.msg = msg;

        init(name, msg);
    }

    private void init(String name, String msg) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.confirm_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView msgTv = (TextView) findViewById(R.id.msgTv);
        Button yesBtn = (Button) findViewById(R.id.yesBtn);
        Button noBtn = (Button) findViewById(R.id.noBtn);

        nameTv.setText((CharSequence) name);
        msgTv.setText(msg);

        yesBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpTask httpTask = new HttpTask();
                httpTask.reqConfirm("YES", noAcnt, Session.getInstance("noAcnt"), callbackConfirm);
            }
        });

        noBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpTask httpTask = new HttpTask();
                httpTask.reqConfirm("NO", noAcnt, Session.getInstance("noAcnt"), callbackConfirm);
            }
        });

    }

    private Callback callbackConfirm = new Callback() {
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
                if(result.equals("YES")){
                    msg.arg1 = 1;
                }
                else if(result.equals("NO")){
                    msg.arg1 = 2;
                }
                else {
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
