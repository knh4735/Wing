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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {
    boolean checkok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        setContent();
    }
    private void setContent() {
        final String id = Session.getInstance("idAcnt");
        final EditText pwEt = (EditText) findViewById(R.id.pwEt),
        confirmPwEt = (EditText) findViewById(R.id.confirmPwEt),
        emailEt = (EditText) findViewById(R.id.emailEt),
        selfEt = (EditText) findViewById(R.id.selfEt);
        pwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                String check = checkPw(id, pwEt, confirmPwEt);
                notice.setText(check);
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                String check = checkPw(id, pwEt, confirmPwEt);
                notice.setText(check);
            }
        });
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticeemailStatus);
                notice.setTextColor(0xFFFF0000);
                if(checkEmail(emailEt))
                    notice.setText("이메일 형식이 올바르지 않습니다.");
                else {
                    notice.setText("올바른 이메일 형식입니다.");
                    checkok = true;
                }
            }
        });

        //TODO 이메일, 이름 유효성 확인하기.(sign에서도)
        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkok) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_1.class);
                    Log.w("intent", "-------------------------------" + intent);
                    startActivity(intent);
                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(ChangeActivity.this, "형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean checkEmail(EditText emailEt){
        String email = emailEt.getText().toString();
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
    private String checkPw(String id, EditText pwEt, EditText confirmPwEt){

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
