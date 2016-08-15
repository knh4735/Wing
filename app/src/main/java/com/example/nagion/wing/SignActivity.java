package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {
    boolean checkok = false, duplechecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        setContent();
    }

    private void setContent() {
        final EditText idEt = (EditText) findViewById(R.id.idEt),
                pwEt = (EditText) findViewById(R.id.pwEt),
                nameEt = (EditText) findViewById(R.id.nameEt),
                confirmPwEt = (EditText) findViewById(R.id.confirmPwEt),
                emailEt = (EditText) findViewById(R.id.emailEt),
                selfEt = (EditText) findViewById(R.id.selfEt),
                nicknameEt = (EditText) findViewById(R.id.nicknameEt),
                phonenumEt = (EditText) findViewById(R.id.phonenumEt);
        Button checkDupBtn = (Button) findViewById(R.id.checkDupBtn);
        final TextView notice0 = (TextView) findViewById(R.id.noticeIdStatus);
        notice0.setTextColor(0xFFFF0000);
        notice0.setText("아이디를 입력해주세요(소문자 및 숫자로 구성된 6-15자)");
        final TextView notice1 = (TextView) findViewById(R.id.noticePwStatus);
        notice1.setTextColor(0xFFFF0000);
        notice1.setText("비밀번호를 입력해주세요");
        final TextView notice2 = (TextView) findViewById(R.id.noticeemailStatus);
        notice2.setTextColor(0xFFFF0000);
        notice2.setText("이메일을 입력해주세요.");
        final TextView notice3 = (TextView) findViewById(R.id.noticeNameStatus);
        notice3.setTextColor(0xFFFF0000);
        notice3.setText("이름을 입력해 주세요.");
        final TextView notice4 = (TextView) findViewById(R.id.noticeNickStatus);
        notice4.setTextColor(0xFFFF0000);
        notice4.setText("닉네임을 입력해 주세요.");
        final TextView notice5 = (TextView) findViewById(R.id.noticephoneStatus);
        notice5.setTextColor(0xFFFF0000);
        notice5.setText("전화번호를 입력해 주세요.(선택)");
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
                if(emailEt.equals("")) {
                    notice2.setText("이메일을 입력해주세요.");
                }
                else if(!checkEmail(emailEt)) {
                    notice2.setText("이메일 형식이 올바르지 않습니다.");
                }
                else {
                    notice2.setText("");
                }
            }
        });
        nameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(nameEt.equals("")||checkName(nameEt)) {
                    notice3.setText("");
                }
                else{
                    notice3.setText("특수문자가 들어가 있거나, 입력하지 않으셨습니다.");
                }
            }
        });
        nicknameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!checkNick(nicknameEt)) {
                    notice4.setText("닉네임을 입력해 주세요.");
                }
                else{
                    notice4.setText("");
                }
            }
        });
        phonenumEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(phonenumEt.equals("")) {
                    notice5.setText("전화번호를 입력해 주세요.(선택)");
                }
                else if(!checkphone(phonenumEt)) {
                    notice5.setText("틀린 전화번호이거나, 입력하지 않으셨습니다.(입력은 선택입니다)");
                }
                else{
                    notice5.setText("");
                }
            }
        });
        idEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(idEt.equals("")||!checkId(idEt)) {
                    notice0.setText("아이디를 입력해주세요(소문자 및 숫자로 구성된 6-15자)");
                }
                else{
                    notice0.setText("");
                }
            }
        });
        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkok = false;
                if((checkPw(idEt, pwEt, confirmPwEt)==0)&&(checkEmail(emailEt))&&(checkName(nameEt))&&checkNick(nicknameEt)) {
                    checkok = true;
                }
                if(checkok&&!phonenumEt.equals("") && (!checkphone(phonenumEt))){
                    checkok = false;
                }
                /*  before code
                if(checkok&&!phonenumEt.equals("")){
                    if(!checkphone(phonenumEt)) {
                        checkok = false;
                    }
                }
                */
                if(!duplechecked) {
                    Toast.makeText(SignActivity.this, "중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(checkok) {//형식이 모두 맞을때.
                    String id = idEt.getText().toString(),
                            pw = pwEt.getText().toString(),
                            nick = nicknameEt.getText().toString(),
                            name = nameEt.getText().toString(),
                            email = emailEt.getText().toString(),
                            phone = phonenumEt.getText().toString(),
                            intro = selfEt.getText().toString();

                    SignTask st = new SignTask();
                    st.execute("signUp", id, pw, nick, name, email, phone, intro);

                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(SignActivity.this, "형식이 맞지 않거나, 중복확인에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkDupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok = false;
                if(!checkId(idEt)) {
                    Toast.makeText(SignActivity.this, "아이디를 입력해주세요(소문자 및 숫자로 구성된 6-15자)", Toast.LENGTH_SHORT).show();
                }
                else {
                    String id = idEt.getText().toString();
                        duplechecked = true;
                        SignTask st = new SignTask();
                        st.execute("duplicatedId", id);
                    }
                }
        });
    }
    private boolean checkEmail(EditText emailEt){
        String email = emailEt.getText().toString();
        if(email.equals("")) {
            return false;
        }
        /* before code
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
        return b;
        */
        return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
    }
    private boolean checkId(EditText idEt){
        String id = idEt.getText().toString();
        if((id.equals(""))||(id.length()<6)) {
            return false;
        }
        /*before code
        boolean b = Pattern.matches("[0-9|a-z]*",id.trim());
        return b;
        */
        return Pattern.matches("[0-9|a-z]*",id.trim());
    }
    private boolean checkName(EditText nameEt){
        String name = nameEt.getText().toString();
        if(name.equals("")) {
            return false;
        }
        /*
        boolean b = Pattern.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*",name.trim());
        return b;
        */
        return Pattern.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*",name.trim());
    }
    private boolean checkNick(EditText nickNameEt){
        String nick = nickNameEt.getText().toString();
        if(nick.equals("")) {
            return false;
        }
        return true;
    }
    private boolean checkphone(EditText phonenumEt){
        String phonenum = phonenumEt.getText().toString();
        if(phonenum.equals("")){
            return false;
        }
        boolean returnval = false;
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

    public class SignTask extends AsyncTask<String, Void, Void> {

        String flag;

        private final HttpTask httpTask;

        SignTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("signUp")){
                httpTask.signUp(params);
                flag = "signUp";
            }
            else if(params[1].equals("duplicatedId")){
                httpTask.duplicatedId(params[1]);
                flag = "duplicatedId";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                if(flag.equals("signUp")) {
                    JSONObject list = httpTask.getReturnObj();
                    String result = list.getString("result");

                    if (result.equals("Success")) {
                        Toast.makeText(SignActivity.this, "정상적으로 가입되었습니다.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignActivity.this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(flag.equals("duplicatedId")){
                    JSONObject list = httpTask.getReturnObj();
                    String result = list.getString("result");

                    if (result.equals("SAFE")) {
                        duplechecked = true;
                        Toast.makeText(SignActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.equals("DUPLICATED")) {
                        Toast.makeText(SignActivity.this, "같은 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.equals("FAIL")){
                        Toast.makeText(SignActivity.this, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                 /* before code
                e.printStackTrace();
                */
                Log.e("e","error occured");
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

}
