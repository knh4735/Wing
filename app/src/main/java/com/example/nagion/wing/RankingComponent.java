package com.example.nagion.wing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RankingComponent extends LinearLayout {

    String name;
    int rank, cnt;

    public RankingComponent(Context context) {
        super(context);
    }

    public RankingComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RankingComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RankingComponent(Context context, int rank,  String name, int cnt){
        super(context);
        this.rank = rank;
        this.name = name;
        this.cnt = cnt;

        init(rank ,name, cnt);
    }

    private void init(int rank, String name, int cnt) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.ranking_component, this, false);
        addView(v);

        TextView rankTv = (TextView) findViewById(R.id.rank);
        TextView nameTv = (TextView) findViewById(R.id.name);
        TextView cntTv = (TextView) findViewById(R.id.cnt);

        rankTv.setText(String.valueOf(rank));
        nameTv.setText(name);
        cntTv.setText(String.valueOf(cnt));
    }
}
