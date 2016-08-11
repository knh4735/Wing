package com.example.nagion.wing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WingComponent extends LinearLayout {

    String name;
    int cnt;

    public WingComponent(Context context) {
        super(context);
    }

    public WingComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WingComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WingComponent(Context context, String name, int cnt){
        super(context);
        this.name = name;
        this.cnt = cnt;
        init(name, cnt);
    }

    private void init(String name, int cnt) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.wing_component, this, false);
        addView(v);

        TextView nameTv = (TextView) findViewById(R.id.nameTv);
        TextView cntTv = (TextView) findViewById(R.id.cntTv);

        nameTv.setText(name);
        cntTv.setText(String.valueOf(cnt));
    }
}
