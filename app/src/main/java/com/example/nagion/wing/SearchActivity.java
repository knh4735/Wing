package com.example.nagion.wing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
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
                if(!tmp.equals(null) && !tmp.equals("")){

                    SearchTask st = new SearchTask();
                    st.execute("searchFriend", tmp);

                    nameEt.setText("");
                }
            }
        });
    }

    public class SearchTask extends AsyncTask<String, Void, Void> {

        private final HttpTask httpTask;

        SearchTask() {
            httpTask = new HttpTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("searchFriend")){
                httpTask.searchFriend(params[1]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            try {
                JSONObject list = httpTask.getReturnObj();
                JSONArray searchComponentList = list.getJSONArray("result");
                Log.w("RETURN", "-------------------------------" + searchComponentList);


                for(int i=0;i<searchComponentList.length();i++){
                    JSONObject searchComponentObj = searchComponentList.getJSONObject(i);
                    String name = searchComponentObj.getString("nick_acnt");

                    SearchComponent wc = new SearchComponent(getApplicationContext(), name);
                    Log.w("RETURN", "-------------------------------" + wc);
                    rl.addView(wc);
                }

            }catch (Exception e){}
        }

        @Override
        protected void onCancelled() {
        }
    }
}
