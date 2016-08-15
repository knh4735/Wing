package com.example.nagion.wing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoticeComponent extends LinearLayout {

    String title, content;

    public NoticeComponent(Context context) {
        super(context);
    }

    public NoticeComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticeComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NoticeComponent(Context context, String title, String content){
        super(context);
        this.title = content;
        init(title, content);
    }

    private void init(String title, String content) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.notice_component, this, false);
        addView(v);

        TextView titleTv = (TextView) findViewById(R.id.title_textView);
        TextView contentTv = (TextView) findViewById(R.id.contentTv);

        titleTv.setText(title);
        contentTv.setText(content);
    }
}
