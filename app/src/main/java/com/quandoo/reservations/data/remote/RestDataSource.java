package com.quandoo.reservations.data.remote;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by felipe on 9/4/16.
 */
public interface RestDataSource {

    void loadCustomers(Callback<List<Customer>> callback);
    void loadTables(Callback<List<Boolean>> callback);
}
