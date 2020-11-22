package com.simcom.sherlock.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.simcom.sherlock.LocationPermissions;
import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.MainActivity;
import com.simcom.sherlock.broadcastReceivers.StopSharingBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class ShareLocationService extends LifecycleService {
    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "share_location_channel";
    public static final String CHANEL_NAME = "share_location";

    public static final int ACTION_START = 1;
    public static final int ACTION_STOP = 2;

    private List<LatLng> locations;

    private static MutableLiveData<Boolean> running = new MutableLiveData<>();
    private static MutableLiveData<List<LatLng>> locationsLiveData = new MutableLiveData<>();

    public static LiveData<Boolean> isRunning() {
        return running;
    }

    public static LiveData<List<LatLng>> getLocations() {
        return locationsLiveData;
    }

    private LocationCallback callback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null) {
                for (Location location : locationResult.getLocations()) {
                    addLocation(location);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getIntExtra("action", 0) == ACTION_START) {
                startForegroundService();
                locations = new ArrayList<>();
                running.postValue(true);
                startSharing();
            } else if (intent.getIntExtra("action", 0) == ACTION_STOP) {
                running.postValue(false);
                stopSelf();
            }
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

    private void addLocation(Location location) {
        if (location != null) {
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            locations.add(pos);
            locationsLiveData.postValue(locations);
            Log.d("LOCATION_SERVICE","Latitude:"+ pos.latitude + "Longitude: "+ pos.longitude);
        }
    }

    @SuppressLint("MissingPermission")
    private void startSharing() {
        LocationRequest request = new LocationRequest()
                .setInterval(5000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (LocationPermissions.hasLocationPermissions(this)) {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(request, callback, Looper.getMainLooper());
        }
    }
}
