package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class MyInfoActivity extends AppCompatActivity {

    /*
    private Button mchange, mdisjoin;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        setContent();
    }

    private void setContent(){
        Button mchange, mdisjoin;
        mchange = (Button)findViewById(R.id.change);
        mdisjoin = (Button)findViewById(R.id.disjoin);
        final TextView nameId = (TextView) findViewById(R.id.nameId),
                nickname = (TextView) findViewById(R.id.nickname),
                memail = (TextView) findViewById(R.id.memail),
                phonenum = (TextView) findViewById(R.id.phonenum),
                intro = (TextView) findViewById(R.id.introduction);
        final String nameSi = Session.getInstance("nameSi"),
                nickAcnt = Session.getInstance("nickAcnt"),
                emailSi = Session.getInstance("emailSi"),
                phoneSi = Session.getInstance("phoneSi"),
                introSi = Session.getInstance("introSi");
        nameId.setText(nameSi);
        nickname.setText(nickAcnt);
        memail.setText(emailSi);
        phonenum.setText(phoneSi);
        intro.setText(introSi);

        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditConfirmActivity.class);
                startActivity(intent);
                MyInfoActivity.this.finish();
            }
        });

        mdisjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpTask httpTask = new HttpTask();
                httpTask.unregister(Session.getInstance("noAcnt"), callbackGetNotice);
            }
        });
    }

    private Callback callbackGetNotice = new Callback() {
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
                Log.w("RETURN", "-------------------------------" + result);

                if(result.equals("Success")){
                    Session.destroySession();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                }
            }
            catch (Exception e){
                // before code
                //e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
