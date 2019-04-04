package com.example.android0211.FCMUse;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMTest_FirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationtoServer(token);
        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
        Log.i("Hello", "Hello0000000000");
    }

    private void sendRegistrationtoServer(String token) {

    }

}
