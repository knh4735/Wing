package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePwActivity extends AppCompatActivity {

    String bfPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        Intent intent = getIntent();
        bfPw = intent.getStringExtra("bfPw");

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
                int tmp = checkPw(id, pwEt, confirmPwEt);
                switch (tmp){
                    case 0: notice.setText("비밀번호가 일치합니다.");break;
                    case 1: notice.setText("비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.");break;
                    case 2: notice.setText("비밀번호에 아이디를 사용할 수 없습니다.");break;
                    case 3: notice.setText("동일 문자를 3번 이상 사용할수 없습니다.");break;
                    case 4: notice.setText("비밀번호가 일치하지 않습니다.");break;
                    case 5: notice.setText("비밀번호 또는 비밀번호 확인을 입력해주세요.");break;
                    default: break;
                }
            }
        });

        confirmPwEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView notice = (TextView) findViewById(R.id.noticePwStatus);
                notice.setTextColor(0xFFFF0000);
                int tmp = checkPw(id, pwEt, confirmPwEt);
                switch (tmp){
                    case 0: notice.setText("비밀번호가 일치합니다.");break;
                    case 1: notice.setText("비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.");break;
                    case 2: notice.setText("비밀번호에 아이디를 사용할 수 없습니다.");break;
                    case 3: notice.setText("동일 문자를 3번 이상 사용할수 없습니다.");break;
                    case 4: notice.setText("비밀번호가 일치하지 않습니다.");break;
                    case 5: notice.setText("비밀번호 또는 비밀번호 확인을 입력해주세요");break;
                    default: break;
                }
            }
        });

        Button toWingList = (Button) findViewById(R.id.finish);
        Log.w("Button",""+ toWingList);
        toWingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkok = false;
                if(checkPw(id, pwEt, confirmPwEt)==0) {
                    checkok = true;
                }
                if(checkok) {
                    String pw = pwEt.getText().toString();

                    ChangePwTask cpt = new ChangePwTask();
                    cpt.execute("changePw", bfPw, pw);
                }
                else{//하나라도 틀린게 있을때.
                    Toast.makeText(ChangePwActivity.this, "형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private int checkPw(String id, EditText pwEt, EditText confirmPwEt){

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


    public class ChangePwTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        ChangePwTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("changePw")){
                httpTask.changePw(Session.getInstance("noAcnt"), params[1], params[2]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                String result = list.getString("result");
                Log.w("RETURN", "-------------------------------" + result);

                if(result.equals("Success")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Log.w("intent", "-------------------------------" + intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
}
