package ru.cwl.otus.hw09;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 17.12.2017 12:49.
 */
public class DbService<T extends DataSet> {
    Connection connection;
    Class<T> clazz;

    public DbService(Connection conn, Class<T> clazz) {
        connection = conn;
        this.clazz = clazz;
    }

    void createTable(Class<T> clazz) {

    }

    void dropTable(Class<T> clazz) {

    }

    boolean isTableExist(Class<T> clazz) {
        return false;
    }


    void save(T user) {
        try {
            String insertSql = "INSERT INTO TEST_USER ( NAME, AGE ) VALUES ( ?, ?)";
            PreparedStatement st = connection.prepareStatement(insertSql);

            fillPS(st,user);

            st.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //    …
    }

    public  void fillPS(PreparedStatement st, T user) {

    }

    T load(long id, Class<T> clazz) {
        String selectByIdSql = "SELECT ID, NAME, AGE FROM TEST_USER WHERE ID=?";
        try {
            PreparedStatement st = connection.prepareStatement(selectByIdSql);
            st.setLong(1,id);
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            if(rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T map(ResultSet rs) {
        return null;
    }

    List<T> load(Class<T> clazz) {
        List<T> resultList = new ArrayList<T>();
        String selectSql = "SELECT ID, NAME, AGE FROM TEST_USER";
        try(Statement st = connection.createStatement()) {
            st.execute(selectSql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                resultList.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //    …
        return resultList;
    }
}
