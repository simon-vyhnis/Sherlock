package com.simcom.sherlock.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.annotation.Nullable;

import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.MainActivity;
import com.simcom.sherlock.broadcastReceivers.StopSharingBroadcastReceiver;

public class ShareLocationService extends LifecycleService {
    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "share_location_channel";
    public static final String CHANEL_NAME = "share_location";

    public static final int ACTION_START = 1;
    public static final int ACTION_STOP = 2;

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent.getIntExtra("action", 0) == ACTION_START) {
            startForegroundService();
        } else if (intent.getIntExtra("action", 0) == ACTION_STOP) {
            //stop
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundService() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(manager);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_location_on_24)
                .setContentTitle("Sharing location")
                .setContentText("You are sharing your location via Sherlock since " + System.currentTimeMillis())
                .setContentIntent(createMainActivityPendingIntent())
                .addAction(R.drawable.ic_baseline_location_on_24, "STOP", createStopIntent());

        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    private PendingIntent createMainActivityPendingIntent() {
        return PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MainActivity.class)
                        .putExtra("action", MainActivity.ACTION_NAVIGATE_TO_SHARE_FRAGMENT),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createStopIntent() {
        return PendingIntent.getBroadcast(this, 0, new Intent(this, StopSharingBroadcastReceiver.class), 0);
    }

    private void createNotificationChannel(NotificationManager manager) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(channel);
    }
}
