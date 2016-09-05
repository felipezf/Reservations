package com.quandoo.reservations.data.entities;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by felipe on 9/2/16.
 */
public class Customer extends RealmObject implements Serializable {

    @PrimaryKey
    private Long id;

    private String customerFirstName;
    private String customerLastName;
    private Integer tableReservationNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Integer getTableReservationNumber() {
        return tableReservationNumber;
    }

    public void setTableReservationNumber(Integer tableReservationNumber) {
        this.tableReservationNumber = tableReservationNumber;
    }
}
