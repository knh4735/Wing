package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RequestActivity extends AppCompatActivity {

    EditText msgEt;
    Button sendBtn;

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

                RequestTask rt = new RequestTask();
                rt.execute("requestFriend", to, msg);
            }
        });
    }



    public class RequestTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        RequestTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("requestFriend")){
                httpTask.requestFriend(params[1], params[2]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {



        }

        @Override
        protected void onCancelled() {
        }
    }
}
