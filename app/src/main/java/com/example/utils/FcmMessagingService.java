package com.example.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.golden.R;

import com.example.golden.notification_message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



import java.util.Map;

/**
 * Created by elluminati on 08-Feb-2017.
 * <p/>
 * This Class is handle a Notification which send by Google FCM server.
 */
public class FcmMessagingService extends FirebaseMessagingService {


    public static final String MESSAGE = "message";
    public static final String Title = "title";
    public static final String LOGIN_IN_OTHER_DEVICE = "2091";
    public static final String STORE_ACCEPTED_YOUR_ORDER = "2001";
    public static final String STORE_START_PREPARING_YOUR_ORDER = "2002";
    public static final String STORE_READY_YOUR_ORDER = "2003";
    public static final String STORE_REJECTED_YOUR_ORDER = "2004";
    public static final String NEW_PRODUCT_NOTICE = "11000";
    public static final String NEW_ITEM_NOTICE = "2088";
    public static final String STORE_CANCELLED_YOUR_ORDER = "2005";
    public static final String DELIVERY_MAN_ACCEPTED = "2081";
    public static final String DELIVERY_MAN_COMING = "2082";
    public static final String DELIVERY_MAN_ARRIVED = "2083";
    public static final String DELIVERY_MAN_PICKED_ORDER = "2084";
    public static final String DELIVERY_MAN_STARTED_DELIVERY = "2085";
    public static final String DELIVERY_MAN_ARRIVED_AT_DESTINATION = "2086";
    public static final String DELIVERY_MAN_COMPLETE_ORDER = "2087";
    public static final String ADMIN_APPROVED = "2006";
    public static final String ADMIN_DECLINE = "2007";
    private static final String CHANNEL_ID = "channel_01";
    private FcmMessagingService context;
    private NotificationManager notifManager;
    private Map<String, String> data;
    private String ItemId = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if (remoteMessage.getNotification() != null) {
//            AppLog.Log(  "MessageBody: " ,"11"+ remoteMessage.getNotification().getBody());
//        }

        Log.d("222222222222222222tile", "ssssssssssssssssssssssss");

        if (remoteMessage != null) {

            data = remoteMessage.getData();
            String message = data.get(MESSAGE);
            String title= data.get(Title);


            if (!TextUtils.isEmpty(message)) {
                sendNotification(message ,title);


            }
        }
    }

    public void sendNotification(String aMessage, String atitle) {
        final int NOTIFY_ID = 2016;

        // There are hardcoding only for show it's just strings
        String name = "feres_chanel";
        String id = "feres_channel_1"; // The user-visible name of the channel.
        String description = aMessage;// The user-visible description of the channel.
        String title = atitle;// The user-visible description of the channel.
//        String title=tile
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);
            intent = new Intent(this, notification_message.class);
            intent.putExtra("message", description);
            intent.putExtra("title", title);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            builder.setContentTitle(this.getString(R.string.app_name))  // required
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(aMessage)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
    else {

            builder = new NotificationCompat.Builder(this);
            intent = new Intent(this, notification_message.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(this.getString(R.string.app_name)) // required
                    .setSmallIcon(R.mipmap.ic_launcher)//REQUIRES
                    .setContentText(aMessage)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }


}


