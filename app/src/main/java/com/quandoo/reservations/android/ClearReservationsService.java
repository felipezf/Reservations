package com.quandoo.reservations.android;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.quandoo.reservations.data.repositories.TableRepository;

public class ClearReservationsService extends IntentService {

    public static final String ACTION_CLEAR_RESERVATIONS = "action.clear_reservations";

//    public ClearReservationsService(){}
    public ClearReservationsService() {
        super("ClearReservationsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if(intent.getAction()!=null && intent.getAction().contentEquals(ACTION_CLEAR_RESERVATIONS))
        TableRepository.getInstance().clearReservations();
    }

    public static void startService(Context context){
        Intent serviceIntent = new Intent(context,ClearReservationsService.class);
        serviceIntent.setAction(ClearReservationsService.ACTION_CLEAR_RESERVATIONS);
        context.startService(serviceIntent);
    }
}
