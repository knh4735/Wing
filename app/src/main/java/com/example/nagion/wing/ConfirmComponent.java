package com.example.nagion.wing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Nagion on 2016. 8. 14..
 */
public class ConfirmComponent  extends LinearLayout {

    String noAcnt;
    String name, msg;

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

        init(name ,msg);
    }

    private void init(String name, String msg) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.ranking_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView msgTv = (TextView) findViewById(R.id.msgTv);
        Button yesBtn = (Button) findViewById(R.id.yesBtn);
        Button noBtn = (Button) findViewById(R.id.noBtn);

        nameTv.setText(name);
        msgTv.setText(msg);

        yesBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmTask ct = new ConfirmTask();
                ct.execute("reqConfirm", "YES", noAcnt, Session.getInstance("noAcnt"));
            }
        });

        noBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmTask ct = new ConfirmTask();
                ct.execute("reqConfirm", "NO", noAcnt, Session.getInstance("noAcnt"));
            }
        });

    }


    public class ConfirmTask extends AsyncTask<String, Void, Void> {

        String flag;
        private final HttpTask httpTask;

        ConfirmTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("reqConfirm")){
                httpTask.reqConfirm(params);
                flag = params[1];
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                JSONObject list = httpTask.getReturnObj();
                String result = list.getString("result");

                if(result.equals("Success")){
                    ConfirmActivity.removeComponent(ConfirmComponent.this);
                }
                else if(result.equals("Fail")){

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
