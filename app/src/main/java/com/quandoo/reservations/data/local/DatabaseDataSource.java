package com.quandoo.reservations.data.local;

import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.data.entities.Table;
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

       RealmResults<Customer> customers =  Realm.getDefaultInstance().where(Customer.class).findAll();
        Realm.getDefaultInstance().close();

        return customers;

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

    @Override
    public TableListWrapper clearTablesReservation() {

        TableListWrapper tableListWrapper = Realm.getDefaultInstance().where(TableListWrapper.class).findFirst();


        if(tableListWrapper!=null && tableListWrapper.getTableList()!=null) {
            Realm.getDefaultInstance().beginTransaction();
            for (Table table : tableListWrapper.getTableList()) {
                table.setStatus(true);
            }
            Realm.getDefaultInstance().commitTransaction();
            Realm.getDefaultInstance().close();
        }

        return tableListWrapper;
    }
}
