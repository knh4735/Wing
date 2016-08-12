package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangeActivity.class);
                startActivity(intent);
                MyInfoActivity.this.finish();
            }
        });

        mdisjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), DisjoinActivity.class);
                //startActivity(intent);
            }
        });
    }
}
