package ru.cwl.otus.hw09;

import java.sql.*;

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
        Statement statement = conn.createStatement();

        statement.executeQuery("SELECT * FROM TEST_USER");
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            System.out.printf("%s %s %s\n", rs.getLong("id"),
                    rs.getString("name"), rs.getInt("age"));
        }

    /*    List<String> names = new ArrayList<>();

        while (!result.isLast()) {
            result.next();
            names.add(result.getString("name"));
        }
        return names;*/

        conn.close();
    }
}
