package ru.cwl.otus.hw10;

import ru.cwl.otus.hw10.model.UserDataSet;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 12:04.
 */
public class Main {
    public static void main(String[] args) {

        DBService dbService = new DBServiceHibernateImpl();

        UserDataSet uds = new UserDataSet();
        uds.setName("Name 1");
        uds.setAge(11);
        UserDataSet uds2 = new UserDataSet();
        uds2.setName("User 2");
        uds2.setAge(22);
        UserDataSet uds3 = new UserDataSet();
        uds3.setName("User 3");
        uds3.setAge(33);

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
