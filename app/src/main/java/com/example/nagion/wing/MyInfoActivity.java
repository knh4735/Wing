package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MyInfoActivity extends AppCompatActivity {

    private Button mchange, mdisjoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        setContent();
    }

    private void setContent(){
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

                InfoTask it = new InfoTask();
                it.execute("unregister");
            }
        });
    }


    public class InfoTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        InfoTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("unregister")){
                httpTask.unregister(Session.getInstance("noAcnt"));
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
                    Session.destroySession();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
