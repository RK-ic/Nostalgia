package com.example.gabekeyner.nostalgia.DatabaseActivitys;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.gabekeyner.nostalgia.MainActivity;
import com.example.gabekeyner.nostalgia.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by GabeKeyner on 10/14/2016.
 */

public class CloudMessagingService extends FirebaseMessagingService {
    private static final String TAG = "CloudMesListenerService";


    //Handles when messages are received with a Image / Title / Text / Sound
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String image = remoteMessage.getNotification().getIcon();
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        String sound = remoteMessage.getNotification().getSound();


        int id = 0;
        Object obj = remoteMessage.getData().get("id");
        if (obj != null) {
            id = Integer.valueOf(obj.toString());
        }
        this.sendNotification(new NotificationData(image, id, title, text, sound));
        Log.d(TAG, "From: " + remoteMessage.getFrom());
    }


    private void sendNotification(NotificationData notificationData) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(NotificationData.TEXT, notificationData.getTextMessage());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pend = PendingIntent.getActivity(this, 0 /*Request Code*/, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = null;
        try {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Nostalgia")
                    .setContentText(URLDecoder.decode(notificationData.getTextMessage(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(notificationSound)
                    .setContentIntent(pend);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        } else {
            Log.d(TAG, "\n" +
                    "Could not create object notificationBuilder");
        }

    }
}
