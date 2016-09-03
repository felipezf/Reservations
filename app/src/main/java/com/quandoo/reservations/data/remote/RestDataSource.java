package com.quandoo.reservations.data.remote;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public interface RestDataSource {

    List<Customer> getCustomerList();
}
