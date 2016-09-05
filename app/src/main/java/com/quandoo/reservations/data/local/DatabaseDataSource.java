package com.quandoo.reservations.data.local;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.entities.TableListWrapper;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by felipe on 9/3/16.
 */
public class DatabaseDataSource implements DatabaseLocalDataSource {

    private static DatabaseDataSource INSTANCE = null;
    private DatabaseDataSource() {}


    public static DatabaseDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseDataSource();
        }
        return INSTANCE;
    }

    @Override
    public RealmResults<Customer> loadCustomers() {

        return Realm.getDefaultInstance().where(Customer.class).findAll();

    }

    @Override
    public TableListWrapper loadTables() {
        return Realm.getDefaultInstance().where(TableListWrapper.class).findFirstAsync();

    }


    @Override
    public void saveTables(Realm.Transaction realmTransaction) {
        Realm.getDefaultInstance().executeTransaction(realmTransaction);
    }

    @Override
    public void updateTable(Realm.Transaction realmTransaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        Realm.getDefaultInstance().executeTransactionAsync(realmTransaction,onSuccess,onError);
    }

    @Override
    public void saveCustomers(Realm.Transaction realmTransaction) {
        Realm.getDefaultInstance().executeTransaction(realmTransaction);
    }

    @Override
    public void updateCustomer(Realm.Transaction realmTransaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        Realm.getDefaultInstance().executeTransactionAsync(realmTransaction,onSuccess,onError);
    }
}
