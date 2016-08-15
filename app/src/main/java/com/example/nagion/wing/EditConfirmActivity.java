package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditConfirmActivity extends AppCompatActivity {
    /*
    private Button mcfbtn;
    private EditText mpwEt;
    */
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

                PwCheckTask pct = new PwCheckTask();
                pct.execute("checkPw", pw);

            }
        });

    }

    public class PwCheckTask extends AsyncTask<String, Void, String> {

        private final HttpTask httpTask;

        PwCheckTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected String doInBackground(String... params) {

            if(params[0].equals("checkPw")){
                httpTask.checkPw(Session.getInstance("noAcnt"), params[1]);
            }

            return params[1];
        }

        @Override
        protected void onPostExecute(String pw) {

            try {
                JSONObject list = httpTask.getReturnObj();
                String result = list.getString("result");
                Log.w("RETURN", "-------------------------------" + result);

                if(result.equals("SAME")) {
                    Intent intent = new Intent(getApplicationContext(), ChangeActivity.class);
                    intent.putExtra("pw", pw);

                    startActivity(intent);
                    EditConfirmActivity.this.finish();
                }
                else if(result.equals("WRONG")){
                    Toast.makeText(EditConfirmActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
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
