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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        setContent();
    }

    private void setContent() {
        final EditText idEt = (EditText) findViewById(R.id.idEt),
        pwEt = (EditText) findViewById(R.id.pwEt),
        confirmPwEt = (EditText) findViewById(R.id.confirmPwEt),
        emailEt = (EditText) findViewById(R.id.emailEt),
        selfEt = (EditText) findViewById(R.id.selfEt);

        final TextView notice1 = (TextView) findViewById(R.id.noticePwStatus);
        notice1.setTextColor(0xFFFF0000);
        notice1.setText("비밀번호를 입력해주세요");
        final TextView notice2 = (TextView) findViewById(R.id.noticeemailStatus);
        notice2.setTextColor(0xFFFF0000);
        notice2.setText("이메일 형식이 올바르지 않습니다.");
        pwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                int tmp = checkPw(idEt, pwEt, confirmPwEt);
                switch (tmp){
                    case 0: notice1.setText("비밀번호가 일치합니다.");break;
                    case 1: notice1.setText("비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.");break;
                    case 2: notice1.setText("비밀번호에 아이디를 사용할 수 없습니다.");break;
                    case 3: notice1.setText("동일 문자를 3번 이상 사용할수 없습니다.");break;
                    case 4: notice1.setText("비밀번호가 일치하지 않습니다.");break;
                    case 5: notice1.setText("비밀번호를 입력해주세요");break;
                    default: break;
                }
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                int tmp = checkPw(idEt, pwEt, confirmPwEt);
                switch (tmp){
                    case 0: notice1.setText("비밀번호가 일치합니다.");break;
                    case 1: notice1.setText("비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.");break;
                    case 2: notice1.setText("비밀번호에 아이디를 사용할 수 없습니다.");break;
                    case 3: notice1.setText("동일 문자를 3번 이상 사용할수 없습니다.");break;
                    case 4: notice1.setText("비밀번호가 일치하지 않습니다.");break;
                    case 5: notice1.setText("비밀번호를 입력해주세요");break;
                    default: break;
                }
            }
        });
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!checkEmail(emailEt))
                    notice2.setText("이메일 형식이 올바르지 않습니다.");
                else
                    notice2.setText("올바른 이메일 형식입니다.");
            }
        });
        //Todo 이름 체크.
        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkok = false;
                if((checkPw(idEt, pwEt, confirmPwEt)==0)&&(checkEmail(emailEt)))
                    checkok = true;
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
    public boolean checkEmail(EditText emailEt){
        String email = emailEt.getText().toString();
        if(email==null)
            return false;
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
        return b;
    }
    private int checkPw(EditText idEt, EditText pwEt, EditText confirmPwEt){
        String id = idEt.getText().toString();
        String pw = pwEt.getText().toString(),
                confirmPw = confirmPwEt.getText().toString();

        if((pw.length()<6)||(pw.length()>16)){
            return 1;
        }

        if(pw.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])")) {
            return 1;
        }
        if(id.indexOf(pw)>-1){
            return 2;
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
            return 3;
        }
        if(!pw.isEmpty() && !confirmPw.isEmpty()){
            if(pw.equals(confirmPw)){
                return 0;
            }
            else {
                return 4;
            }
        }
        return 5;
    }

}
