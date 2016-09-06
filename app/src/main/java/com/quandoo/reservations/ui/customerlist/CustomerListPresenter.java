package com.quandoo.reservations.ui.customerlist;

import android.content.Context;

import com.quandoo.reservations.android.AlarmBroadcastReceiver;
import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.repositories.CustomerDataSource;
import com.quandoo.reservations.data.repositories.CustomerRepository;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public class CustomerListPresenter implements CustomerListContract {

    private CustomerListView customerListView;
    private CustomerDataSource customerRepository;

    public CustomerListPresenter(CustomerListView customerListView) {
        this.customerListView = customerListView;
        this.customerRepository = CustomerRepository.getInstance();
    }

    @Override
    public void populateData() {

        customerRepository.loadCustomers(new CustomerDataSource.LoadData() {
            @Override
            public void onLoaded(List<Customer> customerList) {
                customerListView.showCustomerList(customerList);
            }

            @Override
            public void onNotAvailable() {
                customerListView.showNoDataError();
            }
        });

    }

    @Override
    public void search(String query) {

        customerRepository.findCustomers(query, new CustomerDataSource.LoadData() {
            @Override
            public void onLoaded(List<Customer> customerList) {
                customerListView.showCustomerList(customerList);
            }

            @Override
            public void onNotAvailable() {
                customerListView.showNoDataError();
            }
        });

    }

    @Override
    public void startClearReservationsAlarm(Context context) {
        AlarmBroadcastReceiver.startAlarm(context);
    }
}
