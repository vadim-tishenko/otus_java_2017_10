package ru.cwl.otus.hw09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 16.12.2017 23:26.
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE");

        DbService db = new DbService(conn);
        db.registerEntityClass(UserDataSet.class);

        UserDataSet uds = new UserDataSet();
        uds.name = "User Name";
        uds.age = 33;
        UserDataSet uds2 = new UserDataSet();
        uds2.name = "User Name 2";
        uds2.age = 43;
        UserDataSet uds3 = new UserDataSet();
        uds3.name = "User Name 3";
        uds3.age = 23;


        db.save(uds);
        db.save(uds2);
        db.save(uds3);

        List<UserDataSet> list2 = db.load(UserDataSet.class);
        System.out.println(list2);

        UserDataSet aa2 = db.load(1, UserDataSet.class);
        System.out.println(aa2);

        conn.close();
    }
}
