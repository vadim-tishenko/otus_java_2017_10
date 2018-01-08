package ru.cwl.otus.hw10.model;

import javax.persistence.*;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 13:54.
 */

@Entity
@Table(name = "USERS")
public class UserDataSet implements DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    int age;
    @Embedded
    AddressDataSet address;
    //Set<PhoneDataSet> phones;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }
  
    /*public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }*/
}
