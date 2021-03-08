package com.safetynet.alerts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Person person;

    public MedicalRecord() {

    }
    public MedicalRecord(
            Person person
    ) {
        this.person = person;
    }



    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
