package com.example.nagion.wing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class WingComponent extends LinearLayout {

    String noAcnt, name;
    int cnt;

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

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView cntTv = (TextView) findViewById(R.id.cntTv);
        Button wingBtn = (Button) findViewById(R.id.wingBtn);

        nameTv.setText(name);
        cntTv.setText(String.valueOf(cnt));

        wingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WingTask wt = new WingTask();
                wt.execute("wing", noAcnt);
            }
        });


    }

    public class WingTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        WingTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("wing")){
                httpTask.wing(params[1]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                String result = list.getString("result");
                Log.w("RETURN", "-------------------------------" + result);

                if(result.equals("Success")){
                    //TODO 이 뷰 삭제, wing gcm 수신시 뷰 생성 ㅠㅠ
                }


            }catch (Exception e){}
        }

        @Override
        protected void onCancelled() {
        }
    }
}
