package ru.cwl.otus.hw10;

import ru.cwl.otus.hw10.model.AddressDataSet;
import ru.cwl.otus.hw10.model.PhoneDataSet;
import ru.cwl.otus.hw10.model.UserDataSet;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 12:04.
 */
public class Main {
    public static void main(String[] args) {

        DBService dbService = new DBServiceHibernateImpl();

        UserDataSet uds = new UserDataSet("Name 1",11);
        AddressDataSet a1=new AddressDataSet("kovalevskaya",10);
        uds.setAddress(a1);

        UserDataSet uds2 = new UserDataSet("User 2",22);

        uds.addPhone(new PhoneDataSet("12345-67890"));
        uds.addPhone(new PhoneDataSet("22345-67890"));
        uds.addPhone(new PhoneDataSet("33335-67890"));

        UserDataSet uds3 = new UserDataSet("User 3",33);

        dbService.save(uds);
        dbService.save(uds2);
        dbService.save(uds3);

        UserDataSet aa2 = dbService.load(1, UserDataSet.class);
        System.out.println(aa2);

        List<UserDataSet> list2 = dbService.load(UserDataSet.class);
        System.out.println(list2);

        dbService.close();
    }
}
