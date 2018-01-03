package ru.cwl.otus.hw09;

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

    protected <T extends DataSet> void createTable(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getCreateTable());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T extends DataSet> void dropTable(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getDropTable());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T extends DataSet> void dropTableIfExists(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);
        try {
            PreparedStatement ps = connection.prepareStatement(gen.getDropTableIfExists());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> void save(T t) {

        SQLGenerator gen = entityClasses.get(t.getClass());
        try {
            String insertSql = gen.getInsert();
            PreparedStatement st = connection.prepareStatement(insertSql);
            fillPS(st, t);
            st.execute();
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    private <T extends DataSet> void fillPS(PreparedStatement st, T t) {
        SQLGenerator gen = entityClasses.get(t.getClass());

        int i = 0;
        for (PropertyMapping mapping : gen.mappings) {
            i++;
            try {
                if (mapping.name.equals("ID")) {
                    st.setObject(i, null);
                } else {
                    st.setObject(i, mapping.getValue(t));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        String selectByIdSql = gen.getSelectById();
        try {
            PreparedStatement st = connection.prepareStatement(selectByIdSql);
            st.setLong(1, id);
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                return map(rs, clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected <T extends DataSet> T map(ResultSet rs, Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        try {
            DataSet t = gen.newInst();
            for (PropertyMapping mapping : gen.mappings) {
                Object fieldValue = rs.getObject(mapping.name);
                mapping.setValue(t, fieldValue);
            }
            return (T) t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> List<T> load(Class<T> clazz) {
        SQLGenerator gen = entityClasses.get(clazz);

        List<T> resultList = new ArrayList<T>();
        String selectSql = gen.getSelectAll();
        try (Statement st = connection.createStatement()) {
            st.execute(selectSql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                resultList.add(map(rs, clazz));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void registerEntityClass(Class<? extends DataSet> dataSetClass) {
        entityClasses.put(dataSetClass, new SQLGenerator(dataSetClass));

        // strategy - drop & create
        dropTableIfExists(dataSetClass);
        createTable(dataSetClass);
    }
}
