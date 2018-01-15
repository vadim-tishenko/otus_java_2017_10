package ru.cwl.otus.hw10.model;

import javax.persistence.*;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 13:54.
 */
@Entity
public class AddressDataSet implements DataSet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String street;
    private Integer houseNum;

    public AddressDataSet() {
    }

    public AddressDataSet(String street, Integer houseNum) {
        this.street = street;
        this.houseNum = houseNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNum=" + houseNum +
                '}';
    }
}
