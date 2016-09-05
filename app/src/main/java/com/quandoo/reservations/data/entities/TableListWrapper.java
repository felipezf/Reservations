package com.quandoo.reservations.data.entities;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by felipe on 9/4/16.
 */
public class TableListWrapper extends RealmObject {

    private RealmList<Table> tableList;

    public TableListWrapper(){}

    public TableListWrapper(List<Boolean> tableList){

        this.tableList = new RealmList<>();

        for(Boolean table : tableList){
            this.tableList.add(new Table(table));
        }
    }

    public RealmList<Table> getTableList() {
        return tableList;
    }

    public void setTableList(RealmList<Table> tableList) {
        this.tableList = tableList;
    }

    public void setNewTableList(List<Table> tableList){

        this.tableList = new RealmList<>();
        for(Table table : tableList){
            this.tableList.add(new Table(table.getStatus()));
        }
    }
}
