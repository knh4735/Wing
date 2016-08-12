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

    public SearchComponent(Context context) {
        super(context);
    }

    public SearchComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchComponent(Context context, String name){
        super(context);
        this.name = name;
        init(name);
    }

    private void init(String name) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.search_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);

        nameTv.setText(name);
    }
}
