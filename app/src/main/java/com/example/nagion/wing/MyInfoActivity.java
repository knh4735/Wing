package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyInfoActivity extends AppCompatActivity {

    private Button mchange, mdisjoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        setContent();
    }

    private void setContent(){
        mchange = (Button)findViewById(R.id.change);
        mdisjoin = (Button)findViewById(R.id.disjoin);
        final TextView nameId = (TextView) findViewById(R.id.nameId),
                nickname = (TextView) findViewById(R.id.nickname),
                memail = (TextView) findViewById(R.id.memail),
                phonenum = (TextView) findViewById(R.id.phonenum),
                intro = (TextView) findViewById(R.id.introduction);
        final String nameSi = Session.getInstance("nameSi"),
                nickAcnt = Session.getInstance("nickAcnt"),
                emailSi = Session.getInstance("emailSi"),
                phoneSi = Session.getInstance("phoneSi"),
                introSi = Session.getInstance("introSi");//Todo to 낙현 세션에 자기소개 넣어주세요 introSi로..
        nameId.setText(nameSi);
        nickname.setText(nickAcnt);
        memail.setText(emailSi);
        phonenum.setText(phoneSi);
        intro.setText(introSi);

        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditConfirmActivity.class);
                startActivity(intent);
                MyInfoActivity.this.finish();
            }
        });

        mdisjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 회원 탈퇴 프로시져.
                //Intent intent = new Intent(getApplicationContext(), DisjoinActivity.class);
                //startActivity(intent);
            }
        });
    }
}
