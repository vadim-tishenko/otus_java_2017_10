package ru.cwl.otus.hw10.model;

import javax.persistence.*;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 13:55.
 */
@Entity
@Table(name = "PHONES")
public class PhoneDataSet extends DataSet {
    private String number;
    @ManyToOne
    @JoinColumn(name = "User_ID")
    UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}