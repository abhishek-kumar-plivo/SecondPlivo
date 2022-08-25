package com.example.secondplivo;

import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class MesagingService extends FirebaseMessagingService {
    private static final String TAG = "MesagingService";

    Incoming incomingCall;


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: ");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d(TAG, "onMessageReceived: ");

        Endpoint endpoint = Endpoint.newInstance(getApplicationContext(), true, new EventListener() {
            @Override
            public void onLogin() {
                Log.d(TAG, "onLogin: ");
            }

            @Override
            public void onLogout() {
                Log.d(TAG, "onLogout: ");
            }

            @Override
            public void onLoginFailed() {
                Log.d(TAG, "onLoginFailed: ");
            }

            @Override
            public void onLoginFailed(String message) {

            }

            @Override
            public void onIncomingDigitNotification(String digit) {
                Log.d(TAG, "onIncomingDigitNotification: ");
            }

            @Override
            public void onIncomingCall(Incoming incoming) {
                Log.d(TAG, "onIncomingCall: " + incoming);
                incomingCall = incoming;
                incomingCall.answer();
            }

            @Override
            public void onIncomingCallConnected(Incoming incoming) {
                Log.d(TAG, "onIncomingCallConnected: ");
            }

            @Override
            public void onIncomingCallHangup(Incoming incoming) {
                Log.d(TAG, "onIncomingCallHangup: ");
            }

            @Override
            public void onIncomingCallRejected(Incoming incoming) {

            }

            @Override
            public void onIncomingCallInvalid(Incoming incoming) {

            }

            @Override
            public void onOutgoingCall(Outgoing outgoing) {

            }

            @Override
            public void onOutgoingCallRinging(Outgoing outgoing) {

            }

            @Override
            public void onOutgoingCallAnswered(Outgoing outgoing) {

            }

            @Override
            public void onOutgoingCallRejected(Outgoing outgoing) {

            }

            @Override
            public void onOutgoingCallHangup(Outgoing outgoing) {

            }

            @Override
            public void onOutgoingCallInvalid(Outgoing outgoing) {

            }

            @Override
            public void mediaMetrics(HashMap message) {

            }

            @Override
            public void onPermissionDenied(String message) {

            }
        });
        Log.d(TAG, "onMessageReceived: 1");
        HashMap<String, String> pushMap = new HashMap<>(message.getData());
        endpoint.loginForIncomingWithUsername(Util.userName, Util.password,Util.DeviceToken,"fc75fad807902faf3a3ec43d68073334",pushMap);
    }
}
