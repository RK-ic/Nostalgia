package com.example.gabekeyner.nostalgia.DatabaseActivitys;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by GabeKeyner on 10/14/2016.
 */

public class CloudMessagingInstanceId extends FirebaseInstanceIdService{


    private static String TAG = "Registration";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        System.out.println("Registration.onTokenRefresh TOKEN: " + refreshedToken );
    }
}
