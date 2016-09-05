package com.quandoo.reservations.data;

import android.util.Log;

import com.quandoo.reservations.data.entities.Table;
import com.quandoo.reservations.data.entities.TableListWrapper;
import com.quandoo.reservations.data.local.DatabaseDataSource;
import com.quandoo.reservations.data.local.DatabaseLocalDataSource;
import com.quandoo.reservations.data.remote.RestDataSource;
import com.quandoo.reservations.data.remote.RestRemoteDataSource;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felipe on 9/3/16.
 */
    public class TableRepository implements TableDataSource {

    private static TableRepository INSTANCE = null;

    private RestDataSource tableRemoteDataSource;
    private DatabaseLocalDataSource tableLocalDataSource;
    private TableListWrapper tablesReservationCache;


    private TableRepository() {
        tableRemoteDataSource = RestRemoteDataSource.getInstance();
        tableLocalDataSource = DatabaseDataSource.getInstance();
    }

    public static TableRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TableRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void loadTables(final LoadData callback) {

        if(tablesReservationCache==null){

            TableListWrapper tableListWrapper = tableLocalDataSource.loadTables();

            if(tableListWrapper ==null || !(tableListWrapper.isValid() && tableListWrapper.isLoaded())){
                loadFromNetwork(callback);
            }
            else{
                tablesReservationCache = Realm.getDefaultInstance().copyFromRealm(tableListWrapper);
                callback.onLoaded(tablesReservationCache);
            }
        }
        else{
            callback.onLoaded(tablesReservationCache);
        }
    }

    @Override
    public void updateTable(final int tableReservedPosition, final boolean status) {
        tableLocalDataSource.updateTable(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                TableListWrapper tableListWrapper = realm.where(TableListWrapper.class).findFirst();
                tableListWrapper.getTableList().get(tableReservedPosition).setStatus(status);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {

                tablesReservationCache.getTableList().get(tableReservedPosition).setStatus(status);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("TableRespository",error.getMessage());
            }
        });

    }

    private void loadFromNetwork(final LoadData callback){

        tableRemoteDataSource.loadTables(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response) {

                if(response.isSuccessful()) {
                    TableListWrapper tableListWrapper = new TableListWrapper(response.body());
                    saveTables(tableListWrapper);
                    tablesReservationCache = tableListWrapper;
                    callback.onLoaded(tableListWrapper);
                }
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t) {
                callback.onNotAvailable();
            }
        });
    }

    private void saveTables(final TableListWrapper tableListWrapper) {

        tableLocalDataSource.saveTables(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Realm.getDefaultInstance().delete(TableListWrapper.class);
                TableListWrapper tablesRes = bgRealm.createObject(TableListWrapper.class);

                RealmList<Table> tableRealmList = new RealmList<>();
                for(Table tableTmp : tableListWrapper.getTableList()){
                        Table tableRealm = Realm.getDefaultInstance().createObject(Table.class);
                        tableRealm.setCurrentReserve(tableTmp.getCurrentReserve());
                        tableRealm.setStatus(tableTmp.getStatus());
                        tableRealmList.add(tableRealm);
                }

                tablesRes.setTableList(tableRealmList);
            }
        });
    }

}
