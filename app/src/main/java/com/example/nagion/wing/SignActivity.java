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

import com.google.android.gms.fitness.request.SessionStartRequest;

import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {
    boolean checkok = false;
    EditText idEt, pwEt, confirmPwEt, emailEt, selfEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        idEt = (EditText) findViewById(R.id.idEt);
        pwEt = (EditText) findViewById(R.id.pwEt);
        confirmPwEt = (EditText) findViewById(R.id.confirmPwEt);
        emailEt = (EditText) findViewById(R.id.emailEt);
        selfEt = (EditText) findViewById(R.id.selfEt);


        pwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                String check = checkPw(idEt, pwEt, confirmPwEt);
                notice.setText(check);
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                String check = checkPw(idEt, pwEt, confirmPwEt);
                notice.setText(check);
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

    private String checkPw(EditText idEt, EditText pwEt, EditText confirmPwEt){
        String id = idEt.getText().toString();
        String pw = pwEt.getText().toString(),
                confirmPw = confirmPwEt.getText().toString();

        if((pw.length()<6)||(pw.length()>16)){
            return "비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.";
        }

        if(pw.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])")) {
            return "비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.";
        }
        if(id.indexOf(pw)>-1){
            return "비밀번호에 아이디를 사용할 수 없습니다.";
        }
        int same = 0;
        char chr1,chr2;
        for(int i=0;i<pw.length()-1;i++){
            chr1 = pw.charAt(i);
            chr2 = pw.charAt(i+1);
            if(chr1==chr2){
                same++;
            }
            else if(same!=0){
                same--;
            }
        }
        if(same>1){
            return "동일 문자를 3번 이상 사용할수 없습니다.";
        }
        if(!pw.isEmpty() && !confirmPw.isEmpty()){
            if(pw.equals(confirmPw)){
                checkok = true;
                return "비밀번호가 일치합니다.";
            }
            else {
                return "비밀번호가 일치하지 않습니다.";
            }
        }
        return "";
    }

}
