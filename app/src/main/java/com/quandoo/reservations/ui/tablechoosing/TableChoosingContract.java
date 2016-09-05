package com.quandoo.reservations.ui.tablechoosing;

import com.quandoo.reservations.data.entities.Table;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public interface TableChoosingContract {
    void populateData();
    void reserveTable(List<Table> tableList, int tablePosition);
}
