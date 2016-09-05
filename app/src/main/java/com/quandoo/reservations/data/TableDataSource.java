package com.quandoo.reservations.data;

import com.quandoo.reservations.data.entities.TableListWrapper;

/**
 * Created by felipe on 9/4/16.
 */
public interface TableDataSource {

    interface LoadData {

        void onLoaded(TableListWrapper tableListWrapper);
        void onNotAvailable();
    }

    void loadTables(TableDataSource.LoadData callback);
    void updateTable(final int tableReservedPosition, final boolean status);
}
