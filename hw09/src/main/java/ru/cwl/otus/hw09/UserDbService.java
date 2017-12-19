package ru.cwl.otus.hw09;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vadim.tishenko
 * on 19.12.2017 22:27.
 */
public class UserDbService extends DbService<UserDataSet> {
    public UserDbService(Connection conn) {
        super(conn, UserDataSet.class);
    }

    @Override
    public UserDataSet map(ResultSet rs) {
        UserDataSet res =new UserDataSet();
        try {
            res.id=rs.getLong("ID");
            res.name=rs.getString("NAME");
            res.age=rs.getInt("AGE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void fillPS(PreparedStatement st, UserDataSet user) {
        try {
            st.setString(1,user.name);
            st.setInt(2,user.age);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
