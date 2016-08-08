package com.example.nagion.wing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class MenuActivity extends AppCompatActivity {

    private Button mConfirm, mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        setContent();
    }

    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel);
        mConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.this.finish();
            }
        });
        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.this.finish();
            }
        });
    }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnConfirm:
                    this.finish();
                    break;
                case R.id.btnCancel:
                    this.finish();
                    break;
                default:
                    break;
            }
        }
}
