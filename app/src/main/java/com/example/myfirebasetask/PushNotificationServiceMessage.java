package com.example.myfirebasetask;

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;

import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class PushNotificationServiceMessage extends FirebaseMessagingService {

    boolean flag=false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        String title=remoteMessage.getData().get("title");
        String msgs=remoteMessage.getData().get("body");


        final  String CHANNEL_ID="HEAD_UP_NOTIFICATION";

        NotificationChannel channel=new NotificationChannel(
                CHANNEL_ID,"Heads up Notification",
                NotificationManager.IMPORTANCE_HIGH);

        Intent intent=new Intent(getApplicationContext(),NextPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(),1,intent,
                PendingIntent.FLAG_ONE_SHOT);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
            if(flag) {
                Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(msgs)
                        .setSmallIcon(R.drawable.notifications)
                        .setAutoCancel(true);
                notification.setContentIntent(pendingIntent);

                NotificationManagerCompat.from(this).notify(1, notification.build());
            }
        super.onMessageReceived(remoteMessage);
    }

}
