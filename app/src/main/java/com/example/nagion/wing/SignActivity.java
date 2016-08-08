package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final EditText pwEt = (EditText) findViewById(R.id.pwEt),
                 confirmPwEt = (EditText) findViewById(R.id.confirmPwEt);

        pwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkPw(pwEt,confirmPwEt);
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkPw(pwEt,confirmPwEt);
            }
        });

        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                Log.w("intent","-------------------------------"+intent);
                startActivity(intent);

            }
        });
    }

    private void checkPw(EditText pwEt, EditText confirmPwEt){
        String pw = pwEt.getText().toString(),
                confirmPw = confirmPwEt.getText().toString();


        if(!pw.isEmpty() && !confirmPw.isEmpty()){
            TextView notice = (TextView) findViewById(R.id.noticePwStatus);
            if(pw.equals(confirmPw)){
                notice.setTextColor(0xFF00FF00);
                notice.setText("비밀번호가 일치합니다");
            }
            else {
                notice.setTextColor(0xFFFF0000);
                notice.setText("비밀번호가 일치하지 않습니다");
            }
        }
    }
}
