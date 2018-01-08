package ru.cwl.otus.hw10.model;

import javax.persistence.Embeddable;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 13:54.
 */
@Embeddable
public class AddressDataSet{
    private String street;
    private Integer houseNum;

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
}
