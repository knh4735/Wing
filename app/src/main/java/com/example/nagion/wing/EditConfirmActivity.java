package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditConfirmActivity extends AppCompatActivity {
    private Button mcfbtn;
    private EditText mpwEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_confirm);

        setContent();
    }
    private void setContent(){
        mcfbtn = (Button) findViewById(R.id.cfbtn);
        mpwEt = (EditText)findViewById(R.id.pwcf);
        mcfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 비밀번호를 보내서 확인해야함.
                Intent intent = new Intent(getApplicationContext(), ChangeActivity.class);
                startActivity(intent);
                EditConfirmActivity.this.finish();
            }
        });

    }
}
