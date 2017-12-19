package ru.cwl.otus.hw09;

import java.sql.*;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 16.12.2017 23:26.
 */
public class Main {
    String a="DROP TABLE IF EXISTS TEST_USER;"+
            "CREATE TABLE TEST_USER("+
            "  id bigint(20) NOT NULL auto_increment,"+
            "  name varchar(255),"+
            "  age int(3)"+
            ");";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE");

        UserDbService db=new UserDbService(conn);

        UserDataSet uds=new UserDataSet();
        uds.name="User Name";
        uds.age=33;

        db.save(uds);

        List<UserDataSet> list = db.load(UserDataSet.class);
        System.out.println(list);

        UserDataSet aa = db.load(1, null);
        System.out.println(aa);

        conn.close();
    }
}
