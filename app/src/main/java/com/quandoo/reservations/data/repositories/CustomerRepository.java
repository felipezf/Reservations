package com.quandoo.reservations.data.repositories;

import android.util.Log;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.local.DatabaseDataSource;
import com.quandoo.reservations.data.local.DatabaseLocalDataSource;
import com.quandoo.reservations.data.remote.RestDataSource;
import com.quandoo.reservations.data.remote.RestRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felipe on 9/3/16.
 */
public class CustomerRepository implements CustomerDataSource {

    private static CustomerRepository INSTANCE = null;

    private final Realm realm = Realm.getDefaultInstance();

    private RestDataSource customerRemoteDataSource;
    private DatabaseLocalDataSource customerLocalDataSource;

    private List<Customer> customerCache;

    private CustomerRepository() {
        customerRemoteDataSource = RestRemoteDataSource.getInstance();
        customerLocalDataSource = DatabaseDataSource.getInstance();
    }

    public static CustomerRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void findCustomers(final String query, final CustomerDataSource.LoadData callback) {

        if(customerCache!=null && customerCache.size()>0){

            queryCustomerFields(query, callback);
        }
        else{
            loadCustomers(new LoadData() {
                @Override
                public void onLoaded(List<Customer> customerList) {
                    if(customerCache!=null && customerCache.size()>0) {

                        queryCustomerFields(query, callback);
                    }
                    else{
                        callback.onNotAvailable();
                    }
                }

                @Override
                public void onNotAvailable() {
                    callback.onNotAvailable();
                }
            });
        }
    }

    private void queryCustomerFields(String query, LoadData callback) {

        List<Customer> result = new ArrayList<>();

        for(Customer customer : customerCache){
            if(customer.getCustomerFirstName().toLowerCase().contains(query.toLowerCase()) || customer.getCustomerLastName().toLowerCase().contains(query.toLowerCase())){
                result.add(customer);
            }
        }
        callback.onLoaded(result);
    }

    @Override
    public void loadCustomers(final CustomerDataSource.LoadData callback) {

        if(customerCache==null){

            customerCache = customerLocalDataSource.loadCustomers();

            if(customerCache==null || customerCache.size()==0){
                loadFromNetwork(callback);
            }
            else{
                callback.onLoaded(customerCache);
            }
        }
        else{
            callback.onLoaded(customerCache);
        }
    }

    @Override
    public void saveCustomers(final List<Customer> customerList) {

        customerLocalDataSource.saveCustomers(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Customer.class);
                realm.copyToRealmOrUpdate(customerList);
                realm.close();
            }
        });
    }

    @Override
    public void updateCustomer(final Long customerId, final Integer tablePosition) {

        customerLocalDataSource.updateCustomer(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Customer customerToUpdate = realm.where(Customer.class).equalTo("id",customerId).findFirst();
                customerToUpdate.setTableReservationNumber(tablePosition);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {

                customerCache = customerLocalDataSource.loadCustomers();
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
                Log.e("CustomerRepository",error.getMessage());
            }
        });
    }

    private void loadFromNetwork(final LoadData callback) {
        customerRemoteDataSource.loadCustomers(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

                if(response.isSuccessful()) {
                    customerCache = response.body();
                    saveCustomers(customerCache);
                    callback.onLoaded(customerCache);
                }
                else{
                    callback.onNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                callback.onNotAvailable();
            }
        });
    }
}
