package com.quandoo.reservations.ui.customerlist;

import com.quandoo.reservations.data.entities.Customer;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public interface CustomerListView {

    void hideCustomerList();
    void showCustomerList(List<Customer> customerList);
    void showNoDataError();

}
