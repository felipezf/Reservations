package com.quandoo.reservations.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by felipe on 9/5/16.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction()!=null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
           startAlarm(context);
        }
        else{
            ClearReservationsService.startService(context);
        }

    }

    public static void startAlarm(Context context) {

        long interval = 600000;

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context,12345, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pi);

    }
}
