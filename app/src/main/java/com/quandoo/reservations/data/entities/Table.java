package com.quandoo.reservations.data.entities;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by felipe on 9/4/16.
 */
public class Table extends RealmObject{

    private Boolean status;

    @Ignore
    private boolean isCurrentReserve;

    public Table(){}
    public Table(Boolean status){
        this.status = status;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCurrentReserve() {
        return isCurrentReserve;
    }

    public void setCurrentReserve(Boolean currentReserve) {
        isCurrentReserve = currentReserve;
    }
}
