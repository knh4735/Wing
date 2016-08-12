package com.example.nagion.wing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class Notice extends AppCompatActivity {
    LinearLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        rl = (LinearLayout) findViewById(R.id.wrapper);
        NoticeComponent sc = new NoticeComponent(getApplicationContext(), "title12", 0);
        NoticeComponent sc2 = new NoticeComponent(getApplicationContext(), "title1234", 0);
        rl.addView(sc);
        rl.addView(sc2);
    }
}
