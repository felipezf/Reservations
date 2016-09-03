package com.quandoo.reservations.data.remote;

import com.iwsbrazil.interview.BandsApplication;
import com.iwsbrazil.interview.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by felipe on 12/22/15.
 */
public class RestUtil {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BandsApplication.getContext().getString(R.string.url_bands))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static BandsEndPoint getBandsEndPoint(){

        BandsEndPoint apiService = retrofit.create(BandsEndPoint.class);

        return apiService;
    }


}
