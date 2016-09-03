package com.quandoo.reservations.data.remote;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.entities.TableReservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by felipe on 8/19/16.
 */
public interface ReservationEndpoint {

    @GET("customer-list.json")
    Call<List<Customer>> getCustomerList();

    @GET("table-map.json")
    Call<TableReservation> getBands();
}
