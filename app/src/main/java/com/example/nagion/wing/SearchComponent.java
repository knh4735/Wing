package com.example.nagion.wing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchComponent extends LinearLayout {

    String name;
    int cnt;

    public SearchComponent(Context context) {
        super(context);
    }

    public SearchComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchComponent(Context context, String name, int cnt){
        super(context);
        this.name = name;
        this.cnt = cnt;
        init(name, cnt);
    }

    private void init(String name, int cnt) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.search_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView cntTv = (TextView) findViewById(R.id.cntTv);

        nameTv.setText(name);
        cntTv.setText(String.valueOf(cnt));
    }
}
