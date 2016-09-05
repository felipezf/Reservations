package com.quandoo.reservations.ui.customerlist;

import com.quandoo.reservations.data.CustomerDataSource;
import com.quandoo.reservations.data.CustomerRepository;
import com.quandoo.reservations.data.entities.Customer;

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

            }
        });

    }
}
