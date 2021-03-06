package com.example.nagion.wing;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBUtil db = new DBUtil(MainActivity.this, "Wing", null, 1);
        final boolean isLogined = Session.isSet(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogined) {
                    Intent intent = new Intent(getApplicationContext(), WingActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        },1000);
    }
}
