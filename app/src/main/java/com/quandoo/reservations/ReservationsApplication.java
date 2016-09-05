package com.quandoo.reservations;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by felipe on 9/3/16.
 */
public class ReservationsApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {

        super.onCreate();

        context = getApplicationContext();

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static Context getContext(){
        return ReservationsApplication.context;
    }
}
