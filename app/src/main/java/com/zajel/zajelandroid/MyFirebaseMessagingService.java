package com.zajel.zajelandroid;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {

        Log.e("token",s);
    }

    private final int NOTIFICATION_ID = 239;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
    }

    private void sendNotification(String messageBody, String messageTitle) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.camera_icon)
                //.setColor(getColor(R.color.black))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setGroupSummary(true)
                .setStyle(inboxStyle)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify("Zajel", NOTIFICATION_ID, notificationBuilder.build());
    }
}
