package com.quandoo.reservations.ui.tablechoosing;

import com.quandoo.reservations.data.entities.Table;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public interface TableChoosingView {
    void showTables(List<Table> tablesReservation);
    void markTableAsReserved(List<Table> tableList);
    void unmarkTableAsReserved(List<Table> tableList);
}
