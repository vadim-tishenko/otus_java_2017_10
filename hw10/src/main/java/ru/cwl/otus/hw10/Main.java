package ru.cwl.otus.hw10;

import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
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

        UserDataSet user1 = new UserDataSet("Name 1", 11);
        AddressDataSet a1 = new AddressDataSet("kovalevskaya", 10);
        user1.setAddress(a1);

        UserDataSet user2 = new UserDataSet("User 2", 22);
        AddressDataSet a2 = new AddressDataSet("angarskaya", 12);
        user2.setAddress(a2);

        user1.addPhone(new PhoneDataSet("12345-67890"));
        user1.addPhone(new PhoneDataSet("22345-67890"));
        user1.addPhone(new PhoneDataSet("33335-67890"));

        UserDataSet user3 = new UserDataSet("User 3", 33);

        dbService.save(user1);
        long firstId = user1.getId();
        dbService.save(user2);
        dbService.save(user3);

        UserDataSet loadedUserWithFirstId = dbService.load(firstId, UserDataSet.class);
        System.out.println(loadedUserWithFirstId);

        List<UserDataSet> userList = dbService.load(UserDataSet.class);
        printList(userList);

        dbService.close();
    }
    private static void printList(List list){
        System.out.println("-- user list ---------------");
        for (Object o : list) {
            System.out.println(o);
        }
        System.out.println("-----------------------------");

    }
}
