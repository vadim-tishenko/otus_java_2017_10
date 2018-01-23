package ru.cwl.otus.hw10.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @OneToOne(cascade = CascadeType.ALL)
    AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<PhoneDataSet> phones = new HashSet<PhoneDataSet>();


    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void addPhone(PhoneDataSet phone){
        phones.add(phone);
        phone.setUser(this);
    }

    public void removePhone(PhoneDataSet phone){
        phones.remove(phone);
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
