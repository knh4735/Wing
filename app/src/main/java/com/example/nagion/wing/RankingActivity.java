package com.example.nagion.wing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class RankingActivity extends AppCompatActivity {
    LinearLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rl = (LinearLayout) findViewById(R.id.wrapper);

        for(int i=1;i<10;i++) {
            RankingComponent sc = new RankingComponent(getApplicationContext(), "ranking" + i, 0);
            rl.addView(sc);
        }
    }
}
