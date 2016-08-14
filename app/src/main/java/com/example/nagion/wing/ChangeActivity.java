package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        setContent();
    }
    private void setContent() {
        final EditText emailEt = (EditText) findViewById(R.id.emailEt),
                selfEt = (EditText) findViewById(R.id.selfEt),
                nameEt = (EditText) findViewById(R.id.nameEt),
                nicknameEt = (EditText) findViewById(R.id.nicknameEt),
                phonenumEt = (EditText) findViewById(R.id.phonenumEt);
        final CheckBox nameck = (CheckBox) findViewById(R.id.checkname),
                emailck = (CheckBox) findViewById(R.id.checkemail),
                numberck = (CheckBox) findViewById(R.id.checknum),
                selfck = (CheckBox) findViewById(R.id.checkself);

        final Button pwchange = (Button) findViewById(R.id.pwchange);

        final TextView notice1 = (TextView) findViewById(R.id.noticeemailStatus);
        notice1.setTextColor(0xFFFF0000);
        final TextView notice2 = (TextView) findViewById(R.id.noticeNameStatus);
        notice2.setTextColor(0xFFFF0000);
        final TextView notice3 = (TextView) findViewById(R.id.noticephoneStatus);
        notice3.setTextColor(0xFFFF0000);
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!checkEmail(emailEt))
                    notice1.setText("이메일 형식이 올바르지 않습니다.");
                else {
                    notice1.setText("");
                }
            }
        });
        nameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!checkName(nameEt))
                    notice2.setText("이름에 특수문자가 들어가 있습니다.");
                else{
                    notice2.setText("");
                }
            }
        });
        phonenumEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(phonenumEt.equals("")||checkphone(phonenumEt))
                    notice3.setText("");
                else{
                    notice3.setText("전화번호가 올바르지 않습니다.");
                }
            }
        });
        pwchange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),ChangePwActivity.class);
                startActivity(i);
            }
        });
        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkok = false;
                if(emailEt.equals("")||checkEmail(emailEt)){
                    if(nameEt.equals("")||checkName(nameEt)){
                        if(phonenumEt.equals("")||checkphone(phonenumEt)) {
                            checkok = true;
                        }
                    }
                }
                if(checkok) {
                    //TODO 변경시 정보 받아서 서버로 전송하기.
                    //전달 정보 : emailEt, nameEt, selfEt,nicknameEt.
                    //null값이 전달될 경우 변경사항 없는걸로.
                    //체크 사항은 nameck,emailck,numberck,selfck + .ischecked()로 하시면 됨.
                    Intent intent = new Intent(getApplicationContext(), WingActivity.class);
                    Log.w("intent", "-------------------------------" + intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(ChangeActivity.this, "형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkEmail(EditText emailEt){
        String email = emailEt.getText().toString();
        if(email.equals(""))
            return true;
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
        return b;
    }
    private boolean checkName(EditText nameEt){
        String name = nameEt.getText().toString();
        if(name.equals(""))
            return true;
        boolean b = Pattern.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*",name.trim());
        return b;
    }
    private boolean checkphone(EditText phonenumEt){
        String phonenum = phonenumEt.getText().toString();
        boolean returnval = false;
        if(phonenum.equals(""))
            return true;
        try{
            String regex = "^\\s*(010|011|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phonenum);
            if(m.matches()){
                returnval = true;
            }
            if(returnval&&phonenum.equals("")
                    &&phonenum.length()>0
                    &&phonenum.startsWith("010")){
                phonenum = phonenum.replaceAll("-","");
                if(phonenum.length()!=11){
                    returnval = false;
                }
            }
            return returnval;
        }catch (Exception e){
            return false;
        }
    }
}
