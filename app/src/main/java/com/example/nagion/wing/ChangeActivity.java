package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {

    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Intent intent = getIntent();
        pw = intent.getStringExtra("pw");

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

        nicknameEt.setText(Session.getInstance("nickAcnt"));
        nameEt.setText(Session.getInstance("nameSi"));
        phonenumEt.setText(Session.getInstance("phoneSi"));
        emailEt.setText(Session.getInstance("emailSi"));
        selfEt.setText(Session.getInstance("introSi"));


        final TextView notice1 = (TextView) findViewById(R.id.noticeemailStatus);
        notice1.setTextColor(0xFFFF0000);
        final TextView notice2 = (TextView) findViewById(R.id.noticeNameStatus);
        notice2.setTextColor(0xFFFF0000);
        final TextView notice3 = (TextView) findViewById(R.id.noticephoneStatus);
        notice3.setTextColor(0xFFFF0000);
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!checkEmail(emailEt)) {
                    notice1.setText("이메일 형식이 올바르지 않습니다.");
                }
                else {
                    notice1.setText("");
                }
            }
        });
        nameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!checkName(nameEt)) {
                    notice2.setText("이름에 특수문자가 들어가 있습니다.");
                }
                else{
                    notice2.setText("");
                }
            }
        });
        phonenumEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(phonenumEt.equals("")||checkphone(phonenumEt)) {
                    notice3.setText("");
                }
                else{
                    notice3.setText("전화번호가 올바르지 않습니다.");
                }
            }
        });
        pwchange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),ChangePwActivity.class);
                i.putExtra("bfPw", pw);
                startActivity(i);
            }
        });
        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkok = false;

                if( (emailEt.equals("")||checkEmail(emailEt))&&(emailEt.equals("")||checkEmail(emailEt)) && (phonenumEt.equals("")||checkphone(phonenumEt))) {
                    checkok = true;
                }
                /* before code
                if(emailEt.equals("")||checkEmail(emailEt)){
                    if(nameEt.equals("")||checkName(nameEt)){
                        if(phonenumEt.equals("")||checkphone(phonenumEt)) {
                            checkok = true;
                        }
                    }
                }
                */
                if(checkok) {
                    //전달 정보 : emailEt, nameEt, selfEt,nicknameEt.
                    //null값이 전달될 경우 변경사항 없는걸로.
                    //체크 사항은 nameck,emailck,numberck,selfck + .ischecked()로 하시면 됨.

                    String nick = nicknameEt.getText().toString(),
                            name = nameEt.getText().toString(),
                            phone = phonenumEt.getText().toString(),
                            email = emailEt.getText().toString(),
                            intro = selfEt.getText().toString(),
                            nameCk = String.valueOf(nameck.isChecked()),
                            phoneCk = String.valueOf(numberck.isChecked()),
                            emailCk = String.valueOf(emailck.isChecked()),
                            introCk = String.valueOf(selfck.isChecked());

                    ChangeTask ct = new ChangeTask();
                    ct.execute("changeInfo", pw, nick, name, phone, email, intro, nameCk, phoneCk, emailCk, introCk);
                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(ChangeActivity.this, "형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkEmail(EditText emailEt){
        String email = emailEt.getText().toString();
        if(email.equals("")) {
            return true;
        }
        /* before code
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
        return b;
        */
        return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim());
    }
    private boolean checkName(EditText nameEt){
        String name = nameEt.getText().toString();
        if(name.equals("")) {
            return true;
        }
        /* before code
        boolean b = Pattern.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*",name.trim());
        return b;
        */
        return Pattern.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*",name.trim());
    }
    private boolean checkphone(EditText phonenumEt){
        String phonenum = phonenumEt.getText().toString();
        boolean returnval = false;
        if(phonenum.equals("")) {
            return true;
        }
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

    public class ChangeTask extends AsyncTask<String, Void, String> {

        private final HttpTask httpTask;

        ChangeTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected String doInBackground(String... params) {

            if(params[0].equals("changeInfo")){
                httpTask.changeInfo(params);
            }

            return params[1];
        }

        @Override
        protected void onPostExecute(String pw) {

            try {
                JSONObject list = httpTask.getReturnObj();
                String result = list.getString("result");
                Log.w("RETURN", "-------------------------------" + result);

                if(result.equals("Success")){
                    Intent intent = new Intent(getApplicationContext(), WingActivity.class);
                    Log.w("intent", "-------------------------------" + intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }catch (Exception e){
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

    private Callback callbackChangeInfo = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
             /* before code
                e.printStackTrace();
                */
            Log.e("e","error occured");
            Log.w("fail","---------------------------------------"+request);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            final String strJsonOutput = response.body().string();
            Log.w("String","---------------------------------------"+strJsonOutput);

            try {
                final JSONObject jsonOutput = new JSONObject(strJsonOutput);
                Log.w("JSON","---------------------------------------"+jsonOutput);

                String result = jsonOutput.getString("result");
                Log.w("RESULT", "------------------------"+result);

                if(result.equals("Success")){
                    Intent intent = new Intent(getApplicationContext(), WingActivity.class);
                    Log.w("intent", "-------------------------------" + intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
            catch (Exception e){
                // before code
                //e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
