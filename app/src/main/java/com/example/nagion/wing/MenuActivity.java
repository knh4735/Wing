package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        setContent();
    }

    private void setContent() {
        final Button mnotice, msearch_friend, mrq_confirm, mranking, mmy_info, mlogout;
        mnotice = (Button) findViewById(R.id.notice);
        msearch_friend = (Button) findViewById(R.id.search_friend);
        mrq_confirm = (Button) findViewById(R.id.rq_confirm);
        mranking = (Button) findViewById(R.id.ranking);
        mmy_info = (Button) findViewById(R.id.my_info);
        mlogout = (Button) findViewById(R.id.logout);
        mnotice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        msearch_friend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        mrq_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        mranking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        mmy_info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        mlogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                MenuTask mt = new MenuTask();
                mt.execute("logout");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);

            }
        });
    }

    public class MenuTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        MenuTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("logout")){
                httpTask.logout(Session.getInstance("noAcnt"), Session.getInstance("token"));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Session.destroySession();
        }

        @Override
        protected void onCancelled() {
        }
    }

}
