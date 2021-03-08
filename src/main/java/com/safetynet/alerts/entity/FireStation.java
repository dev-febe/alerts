package com.safetynet.alerts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FireStation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Address address;

    private Long station;
    public FireStation() {
    }

    public FireStation(Address address, Long station) {
        this.address = address;
        this.station = station;
    }



    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getStation() {
        return station;
    }

    public void setStation(Long station) {
        this.station = station;
    }
}
