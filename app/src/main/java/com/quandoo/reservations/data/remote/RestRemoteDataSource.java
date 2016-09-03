package com.quandoo.reservations.data.remote;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felipe on 9/3/16.
 */
public class RestRemoteDataSource implements RestDataSource{


    @Override
    public List<Customer> getCustomerList() {

        RestUtil.getReservationEndpoint().getCustomerList().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }
}
