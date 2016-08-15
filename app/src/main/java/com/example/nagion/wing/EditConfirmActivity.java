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
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class EditConfirmActivity extends AppCompatActivity {
    /*
    private Button mcfbtn;
    private EditText mpwEt;
    */


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                if(msg.arg1 == 1) {
                    Bundle data = msg.getData();
                    String pw = data.getString("pw");

                    Intent intent = new Intent(getApplicationContext(), ChangeActivity.class);
                    intent.putExtra("pw", pw);

                    startActivity(intent);
                    EditConfirmActivity.this.finish();
                }
                else if(msg.arg1 == 2){
                    Toast.makeText(EditConfirmActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditConfirmActivity.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){}

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_confirm);

        setContent();
    }
    private void setContent(){
        Button mcfbtn;
        final EditText mpwEt;

        mcfbtn = (Button) findViewById(R.id.cfbtn);
        mpwEt = (EditText)findViewById(R.id.pwcf);

        mcfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = mpwEt.getText().toString();

                HttpTask httpTask = new HttpTask();
                httpTask.checkPw(Session.getInstance("noAcnt", getApplicationContext()), pw, callbackCheckPw);

            }
        });

    }


    private Callback callbackCheckPw = new Callback() {
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
                if(result.equals("SAME")) {
                    String pw = jsonOutput.getString("pw");
                    Bundle data = new Bundle();
                    data.putString("pw", pw);

                    msg.setData(data);
                    msg.arg1 = 1;
                }
                else if(result.equals("WRONG")){
                    msg.arg1 = 2;
                }
                handler.sendMessage(msg);

            }
            catch (Exception e){
                // before code
                //e.printStackTrace();

                Log.e("e","error occured");
            }
        }
    };
}
