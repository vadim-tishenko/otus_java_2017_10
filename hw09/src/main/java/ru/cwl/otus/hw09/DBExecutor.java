package ru.cwl.otus.hw09;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vadim.tishenko
 * on 17.12.2017 12:49.
 */
public class DBExecutor {
    Connection connection;

    <T extends DataSet> void save(T user) {
        try {
            Statement stt = connection.createStatement();
            String insert_sql="";
            stt.execute(insert_sql);
            ResultSet resultSet = stt.getResultSet();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //    …
    }

    <T extends DataSet> T load(long id, Class<T> clazz) {
        try {
            T result=clazz.newInstance();
            Statement st = connection.createStatement();
            String select_sql = "";
            st.execute(select_sql);
            ResultSet rs = st.getResultSet();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //    …
        return null;
    }
}
