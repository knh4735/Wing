package com.example.nagion.wing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class LoginActivity extends AppCompatActivity {
/*
    private EditText idEt;
    private EditText pwEt;
    private Button loginBtn;
    private Button signBtn;
*/

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText idEt;
        final EditText pwEt;
        Button loginBtn;
        Button signBtn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEt = (EditText) findViewById(R.id.idEt);
        pwEt = (EditText) findViewById(R.id.pwEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        signBtn = (Button) findViewById(R.id.signBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = "a";//idEt.getText().toString();
                String pw = "a";//pwEt.getText().toString();

                if(id.equals("")){
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(pw.equals("")){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }

                mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        SharedPreferences sharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(context);
                        boolean sentToken = sharedPreferences
                                .getBoolean("sentTokenToServer", false);
                        if (sentToken && Session.isSet()) {
                            Intent i = new Intent(getApplicationContext(), WingActivity.class);
                            startActivity(i);
                            LoginActivity.this.finish();
                        } else {
                            Log.w("error","---------------------------------"+R.string.token_error_message);
                        }
                    }
                };

                registerReceiver();

                if (checkPlayServices()) {
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(LoginActivity.this, RegistrationIntentService.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pw", pw);
                    startService(intent);
                }
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter("registrationComplete"));
            isReceiverRegistered = true;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 9000)
                        .show();
            } else {
                Log.i("Login", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
