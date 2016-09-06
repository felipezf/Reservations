package com.quandoo.reservations.ui.customerlist;

import android.content.Context;

/**
 * Created by felipe on 9/3/16.
 */
public interface CustomerListContract {

    void populateData();
    void search(String query);
    void startClearReservationsAlarm(Context context);

}
