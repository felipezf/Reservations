package com.quandoo.reservations.data.entities;

import java.util.List;

/**
 * Created by felipe on 9/2/16.
 */
public class TableReservation {

    List<Boolean> reserves;

    public List<Boolean> getReserves() {
        return reserves;
    }

    public void setReserves(List<Boolean> reserves) {
        this.reserves = reserves;
    }
}
