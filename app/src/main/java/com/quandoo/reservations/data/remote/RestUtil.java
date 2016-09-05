package com.quandoo.reservations.data.remote;


import com.quandoo.reservations.R;
import com.quandoo.reservations.ReservationsApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestUtil {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ReservationsApplication.getContext().getString(R.string.rest_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ReservationEndpoint getReservationEndpoint(){

        ReservationEndpoint apiService = retrofit.create(ReservationEndpoint.class);

        return apiService;
    }


}
