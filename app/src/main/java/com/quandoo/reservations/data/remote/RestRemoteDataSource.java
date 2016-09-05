package com.quandoo.reservations.data.remote;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by felipe on 9/3/16.
 */
public class RestRemoteDataSource implements RestDataSource{

    private static RestRemoteDataSource INSTANCE = null;
    private RestRemoteDataSource() {}


    public static RestRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void loadCustomers(Callback<List<Customer>> callback) {
        RestUtil.getReservationEndpoint().getCustomerList().enqueue(callback);
    }

    @Override
    public void loadTables(Callback<List<Boolean>> callback) {
        RestUtil.getReservationEndpoint().getTableList().enqueue(callback);
    }
}
