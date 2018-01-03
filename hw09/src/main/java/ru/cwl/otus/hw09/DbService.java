package ru.cwl.otus.hw09;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadim.tishenko
 * on 17.12.2017 12:49.
 */
public class DbService {
    Connection connection;
    private final Map<Class<? extends DataSet>, SQLGenerator> entityClasses;

    public DbService(Connection conn) {
        connection = conn;
        entityClasses = new IdentityHashMap<>();
    }

    protected <T> void  createTable(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getCreateTable());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T> void dropTable(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getDropTable());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T> void dropTableIfExists(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getDropTableIfExists());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> void save(T t) {

        SQLGenerator gen = entityClasses.get(t.getClass());
        try {
            String insertSql = gen.getInsert();
            PreparedStatement st = connection.prepareStatement(insertSql);

            fillPS(st, t);

            st.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //    …
    }

     private  <T> void fillPS(PreparedStatement st, T t) {
         SQLGenerator gen = entityClasses.get(t.getClass());

         int i = 0;
        for (FieldMapping mapping : gen.mappings) {
            i++;
            try {if(mapping.name.equals("ID")){
                st.setObject(i, null);

            }else {
                st.setObject(i, mapping.getter.invoke(t));
            }
            } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public <T> T load(long id, Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        String selectByIdSql = gen.getSelectById();
        try {
            PreparedStatement st = connection.prepareStatement(selectByIdSql);
            st.setLong(1, id);
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                return map(rs,clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected <T> T map(ResultSet rs,Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        try {
            T t = clazz.newInstance();
            for (FieldMapping mapping : gen.mappings) {
                Object fieldValue = rs.getObject(mapping.name);
                mapping.setter.invoke(t,fieldValue);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> load(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        List<T> resultList = new ArrayList<T>();
        String selectSql = gen.getSelectAll();
        try (Statement st = connection.createStatement()) {
            st.execute(selectSql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                resultList.add(map(rs,clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void registerEntityClass(Class<? extends DataSet> dataSetClass) {
        entityClasses.put(dataSetClass,new SQLGenerator(dataSetClass));
        dropTableIfExists(dataSetClass);
        createTable(dataSetClass);
    }
}
