package com.example.nagion.wing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;

public class Search extends AppCompatActivity {
    EditText nameEt;
    Button makeBtn;
    LinearLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        nameEt = (EditText) findViewById(R.id.nameEt);
        makeBtn = (Button) findViewById(R.id.makeBtn);
        rl = (LinearLayout) findViewById(R.id.wrapper);

        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = nameEt.getText().toString();
                if(tmp != null && tmp != ""){

                    SearchComponent sc = new SearchComponent(getApplicationContext(), tmp, 0);
                    rl.addView(sc);

                    nameEt.setText("");
                }
            }
        });
    }
}
