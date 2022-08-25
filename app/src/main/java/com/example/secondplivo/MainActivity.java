package com.example.secondplivo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button loginButton;
    Button callButton;
    Button logoutButton;
    EditText callIdText;
    Endpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.login);
        callButton = findViewById(R.id.call);
        logoutButton = findViewById(R.id.logout);
        callIdText = findViewById(R.id.callId);
        loginButton.setOnClickListener(view -> {
            login();
        });
    }


    private void login() {
        Log.d(TAG, "login: ");
        endpoint = Endpoint.newInstance(getApplicationContext(),true ,new EventListener() {
            @Override
            public void onLogin() {
                Log.d(TAG, "onLogin: ");
                runOnUiThread(() -> {
                    Log.d(TAG, "onLogin: dd");
                    Toast.makeText(MainActivity.this,"Registered",Toast.LENGTH_LONG).show();
                    loginButton.setVisibility(View.GONE);
                    callButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.VISIBLE);
                    callIdText.setVisibility(View.VISIBLE);
                    logoutButton.setOnClickListener(view -> logout());
                    callButton.setOnClickListener(view -> call());
                });
            }

            @Override
            public void onLogout() {
                Log.d(TAG, "onLogout: ");
                loginButton.setVisibility(View.VISIBLE);
                callButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
            }

            @Override
            public void onLoginFailed() {
                Log.d(TAG, "onLoginFailed: ");
            }

            @Override
            public void onLoginFailed(String message) {
                Log.d(TAG, "onLoginFailed: ");
            }


            @Override
            public void onIncomingDigitNotification(String digit) {
                Log.d(TAG, "onIncomingDigitNotification: ");
            }

            @Override
            public void onIncomingCall(Incoming incoming) {
                Log.d(TAG, "onIncomingCall: ");
                Handler h = new Handler(Looper.getMainLooper());
                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        incoming.answer();
                    }
                }, 1000);

            }

            @Override
            public void onIncomingCallConnected(Incoming incoming) {
                Log.d(TAG, "onIncomingCallConnected: ");
                incoming.answer();
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,"Answered connection",Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onIncomingCallHangup(Incoming incoming) {
                Log.d(TAG, "onIncomingCallHangup: ");
            }

            @Override
            public void onIncomingCallRejected(Incoming incoming) {
                Log.d(TAG, "onIncomingCallRejected: ");
            }

            @Override
            public void onIncomingCallInvalid(Incoming incoming) {
                Log.d(TAG, "onIncomingCallInvalid: ");
            }

            @Override
            public void onOutgoingCall(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCall: ");
            }

            @Override
            public void onOutgoingCallRinging(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCallRinging: ");
            }

            @Override
            public void onOutgoingCallAnswered(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCallAnswered: ");
            }

            @Override
            public void onOutgoingCallRejected(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCallRejected: ");
            }

            @Override
            public void onOutgoingCallHangup(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCallHangup: ");
            }

            @Override
            public void onOutgoingCallInvalid(Outgoing outgoing) {
                Log.d(TAG, "onOutgoingCallInvalid: ");
            }

            @Override
            public void mediaMetrics(HashMap message) {
                Log.d(TAG, "mediaMetrics: ");
            }

            @Override
            public void onPermissionDenied(String message) {

            }

        });

//        endpoint.login("abhishek32668134349748736166","12345");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult ->{
                Log.d(TAG, "login: device_token :"+instanceIdResult.getToken());
                Util.setDeviceToken(instanceIdResult.getToken());
                endpoint.login( "abhishek32668134349748736166", "12345",instanceIdResult.getToken(),"fc75fad807902faf3a3ec43d68073334");});
    }

    private void logout() {
        Log.d(TAG, "logout: ");
        endpoint.logout();
    }

    private void call(){
        endpoint.createOutgoingCall().call(callIdText.getText().toString());
    }

}