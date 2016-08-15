package com.example.nagion.wing;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchComponent extends LinearLayout {

    String no;
    String name;

    public SearchComponent(Context context) {
        super(context);
    }

    public SearchComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchComponent(Context context, String name, String no){
        super(context);
        this.name = name;
        this.no = no;
        init(name);
    }

    private void init(String name) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.search_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        Button requestBtn = (Button) findViewById(R.id.requestBtn);

        nameTv.setText(name);

        requestBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();

                Intent intent = new Intent(context, RequestActivity.class);
                intent.putExtra("to", no);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
