package ru.cwl.otus.jawa.hw12;

import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.model.AddressDataSet;
import ru.cwl.otus.hw10.model.PhoneDataSet;
import ru.cwl.otus.hw10.model.UserDataSet;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 21:09.
 */
public class PayLoadService implements Runnable {
    private final DBService dbService;

    UserDataSet createUser(int n) {
        UserDataSet user = new UserDataSet("Name " + n, n * 2);
        user.setAddress(new AddressDataSet("stret" + n + 1, n + 3));

        user.addPhone(new PhoneDataSet("12345-67890"));
        user.addPhone(new PhoneDataSet("22345-67890"));
        user.addPhone(new PhoneDataSet("33335-67890"));
        return user;
    }

    public PayLoadService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            UserDataSet user = createUser(i);
            dbService.save(user);
            dbService.load(1, UserDataSet.class);
            dbService.load(25, UserDataSet.class);
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
