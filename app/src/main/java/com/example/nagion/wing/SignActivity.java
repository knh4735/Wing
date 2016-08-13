package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {
    boolean checkok = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final EditText pwEt = (EditText) findViewById(R.id.pwEt),
                 confirmPwEt = (EditText) findViewById(R.id.confirmPwEt);


        pwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                if(spwdCheck(pwEt)) {
                    if (checkPw(pwEt, confirmPwEt)) {
                        notice.setText("비밀번호가 일치합니다.");
                        checkok = true;
                    } else {
                        notice.setText("비밀번호가 일치하지 않습니다.");
                        checkok = false;
                    }
                }
                else{
                    notice.setText("비밀번호에 특수문자가 포함되어 있습니다.");
                    checkok = false;
                }
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                if(spwdCheck(pwEt))
                    if(checkPw(pwEt,confirmPwEt)){
                        notice.setText("비밀번호가 일치합니다.");
                        checkok = true;
                    }
                    else{
                        notice.setText("비밀번호가 일치하지 않습니다.");
                    }
                else{
                    notice.setText("비밀번호에 특수문자가 포함되어 있습니다.");
                }
            }
        });

        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkok) {//형식이 모두 맞을때.
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_1.class);
                    Log.w("intent", "-------------------------------" + intent);
                    startActivity(intent);
                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(SignActivity.this, "형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPw(EditText pwEt, EditText confirmPwEt){
        String pw = pwEt.getText().toString(),
                confirmPw = confirmPwEt.getText().toString();

        if(!pw.isEmpty() && !confirmPw.isEmpty()){
            if(pw.equals(confirmPw)){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    private boolean spwdCheck(EditText pwEt){
        String pw = pwEt.getText().toString();
        if(pw.matches("[0-9|a-z|A-Z]*"))
            return true;
        else return false;
    }

}
