package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class RequestActivity extends AppCompatActivity {

    EditText msgEt;
    Button sendBtn;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                Toast.makeText(RequestActivity.this, "친구 요청을 보냈습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(msg.arg1 == 2){
                Toast.makeText(RequestActivity.this, "이미 친구 요청한 사용자입니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RequestActivity.this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        final Intent intent = getIntent();


        msgEt = (EditText) findViewById(R.id.msgEt);
        sendBtn = (Button) findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = intent.getStringExtra("to");
                String msg = msgEt.getText().toString();

                HttpTask httpTask = new HttpTask();
                httpTask.requestFriend(to, msg, callbackRequest);
            }
        });
    }


    private Callback callbackRequest = new Callback() {
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
                else if(result.equals("Already")){
                    msg.arg1 = 2;
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
