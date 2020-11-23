package com.simcom.sherlock.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.simcom.sherlock.model.Repository;
import com.simcom.sherlock.services.ShareLocationService;

public class StopSharingBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startForegroundService(new Intent(context, ShareLocationService.class).putExtra("action", ShareLocationService.ACTION_STOP));
        Toast.makeText(context,"Stopping sharing",Toast.LENGTH_SHORT).show();
        Repository.getInstance().stopSharing();
    }
}

