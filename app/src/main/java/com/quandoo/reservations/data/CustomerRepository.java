package com.quandoo.reservations.data;

import android.util.Log;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.local.DatabaseDataSource;
import com.quandoo.reservations.data.local.DatabaseLocalDataSource;
import com.quandoo.reservations.data.remote.RestDataSource;
import com.quandoo.reservations.data.remote.RestRemoteDataSource;

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
    public void loadCustomers(final CustomerDataSource.LoadData callback) {

        if(customerCache==null){

            customerCache = customerLocalDataSource.loadCustomers();

            if(customerCache==null || customerCache.size( )==0){
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

                Realm.getDefaultInstance().delete(Customer.class);
                Realm.getDefaultInstance().copyToRealmOrUpdate(customerList);
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
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
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
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                callback.onNotAvailable();
            }
        });
    }
}
