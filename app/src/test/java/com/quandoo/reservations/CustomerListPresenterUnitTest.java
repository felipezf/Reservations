package com.quandoo.reservations;

import com.quandoo.reservations.data.repositories.CustomerRepository;
import com.quandoo.reservations.ui.customerlist.CustomerListContract;
import com.quandoo.reservations.ui.customerlist.CustomerListPresenter;
import com.quandoo.reservations.ui.customerlist.CustomerListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerListPresenterUnitTest {

    @Mock
    CustomerListView view;

    @Mock
    CustomerRepository customerRepository;

    private CustomerListContract presenter;


    @Before
    public void setUp() throws Exception {

        presenter = new CustomerListPresenter(view,customerRepository);
    }

    @Test
    public void checkPopulateData(){

        presenter.populateData();
        verify(view,times(1)).hideCustomerList();
    }
}