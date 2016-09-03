package com.quandoo.reservations;

import android.app.Application;
import android.content.Context;

/**
 * Created by felipe on 9/3/16.
 */
public class ReservationsApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    public static Context getContext(){
        return ReservationsApplication.context;
    }
}
