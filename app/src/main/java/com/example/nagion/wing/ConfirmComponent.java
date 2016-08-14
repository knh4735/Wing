package com.example.nagion.wing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nagion on 2016. 8. 14..
 */
public class ConfirmComponent  extends LinearLayout {

    String name, msg;

    public ConfirmComponent(Context context) {
        super(context);
    }

    public ConfirmComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConfirmComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ConfirmComponent(Context context, String name, String msg){
        super(context);
        this.name = name;
        this.msg = msg;

        init(name ,msg);
    }

    private void init(String name, String msg) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.ranking_component, this, false);
        addView(v);

        TextView rankTv = (TextView) findViewById(R.id.rank);
        TextView nameTv = (TextView) findViewById(R.id.name);
        TextView cntTv = (TextView) findViewById(R.id.cnt);

     //   rankTv.setText(String.valueOf(rank));
        nameTv.setText(name);
       // cntTv.setText(String.valueOf(cnt));
    }
}
