package com.quandoo.reservations.data;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

/**
 * Created by felipe on 9/4/16.
 */
public interface CustomerDataSource {

    interface LoadData {

        void onLoaded(List<Customer> customerList);
        void onNotAvailable();
    }

    void loadCustomers(CustomerDataSource.LoadData callback);
    void saveCustomers(List<Customer> customerList);
    void updateCustomer(Long customerId, Integer tablePosition);
}
