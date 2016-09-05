package com.quandoo.reservations.data.local;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.entities.TableListWrapper;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by felipe on 9/4/16.
 */
public interface DatabaseLocalDataSource {

    RealmResults<Customer> loadCustomers();
    TableListWrapper loadTables();
    void saveTables(Realm.Transaction realmTransaction);
    void updateTable(Realm.Transaction realmTransaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);
    void saveCustomers(Realm.Transaction realmTransaction);
    void updateCustomer(Realm.Transaction realmTransaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);
}
