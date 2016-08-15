package com.example.nagion.wing;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class VibeActivity extends AppCompatActivity {

    Button vibeBtn, finishBtn;

    ArrayList<Long> ptrn = new ArrayList<Long>();

    long pattern[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);

        vibeBtn = (Button) findViewById(R.id.vibe);
        finishBtn = (Button) findViewById(R.id.complete);

        vibeBtn.setOnTouchListener(new View.OnTouchListener() {

            GregorianCalendar gcDown = null, gcUp = null;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        gcDown = new GregorianCalendar();
                        Log.w("ButtonDown", "=----------------------------------" + gcDown.getTime().getTime());//(Calendar.ZONE_OFFSET));
                        break;
                    case MotionEvent.ACTION_UP :
                        if(gcDown != null) {
                            gcUp = new GregorianCalendar();
                            Log.w("ButtonUp", "=----------------------------------" + gcUp.getTime().getTime());

                            long interval = gcUp.getTime().getTime() - gcDown.getTime().getTime();

                            Log.w("TimeInterval", "=----------------------------------" + interval);

                            ptrn.add(interval);
                            ptrn.add(Long.parseLong("1000"));
                        }
                        break;
                }
                return false;
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pattern = new long[ptrn.size()+1];
                pattern[0] = 0;
                int i = 1;
                for (Long l : ptrn) {
                    pattern[i++] = l;
                }

                ptrn = new ArrayList<Long>();

                Vibrator vibe;
                vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(pattern, -1);

            }
        });

    }
}
