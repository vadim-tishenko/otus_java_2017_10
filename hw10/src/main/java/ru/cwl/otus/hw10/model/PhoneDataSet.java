package ru.cwl.otus.hw10.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 13:55.
 */
@Entity
public class PhoneDataSet implements DataSet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id=id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}