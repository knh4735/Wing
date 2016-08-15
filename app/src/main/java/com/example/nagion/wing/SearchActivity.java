package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity {
    EditText nameEt;
    Button makeBtn;
    LinearLayout rl;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                Bundle data = msg.getData();
                String jsonString = data.getString("jsonString");
                JSONArray searchComponentList = new JSONArray(jsonString);
                Log.w("RETURN", "-------------------------------" + searchComponentList);


                for(int i=0;i<searchComponentList.length();i++){
                    JSONObject searchComponentObj = searchComponentList.getJSONObject(i);
                    String name = searchComponentObj.getString("nick_acnt");
                    String no = searchComponentObj.getString("no_acnt");

                    SearchComponent wc = new SearchComponent(getApplicationContext(), name, no);
                    Log.w("RETURN", "-------------------------------" + wc);
                    rl.addView(wc);
                }

            }catch (Exception e){}

        }
    };


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
                if(!tmp.equals("")){

                    HttpTask httpTask = new HttpTask();
                    httpTask.searchFriend(tmp, callbackSearch);

                    nameEt.setText("");
                }
            }
        });
    }


    private Callback callbackSearch = new Callback() {
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

                Message msg = handler.obtainMessage();
                Bundle data = new Bundle();
                data.putString("jsonString", result);
                msg.setData(data);
                handler.sendMessage(msg);

            }
            catch (Exception e){
                // before code
                e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
