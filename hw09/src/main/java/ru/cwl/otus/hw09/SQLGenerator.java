package ru.cwl.otus.hw09;


import sun.reflect.misc.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 02.01.2018 10:58.
 * <p>
 * generate SQL.
 */
public class SQLGenerator {
    Class<? extends DataSet> clazz;
    Method[] methods;
    List<PropertyMapping> mappings = new ArrayList<>();


    public SQLGenerator(Class<? extends DataSet> userDataSetClass) {
        clazz = userDataSetClass;
        methods = MethodUtil.getMethods(clazz);
        refreshColumnMapping();
    }

    DataSet newInst(){
        try {
            return  clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
           throw new RuntimeException(e);
        }
    }

    String getTableName() {
        String name = clazz.getSimpleName();
        int ind = name.indexOf("DataSet");
        if (ind > 0) {
            return name.substring(0, ind).toUpperCase() + "S";
        }
        return name;
    }

    String[] getColumnNames() {

        ArrayList<String> methodList = new ArrayList<>();
        for (PropertyMapping mapping : mappings) {
            methodList.add(mapping.name);
        }

        return methodList.toArray(new String[]{});
    }

    private void refreshColumnMapping() {

        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                if (method.getName().equals("getClass")) continue;
                final String fieldName = method.getName().substring(3).toUpperCase();
                PropertyMapping fm = new PropertyMapping();
                fm.name = fieldName;
                fm.getter = method;
                mappings.add(fm);
            }
        }

        for (PropertyMapping m : mappings) {
            if (m.name.equals("ID")) {
                m.sqlType = "BIGINT IDENTITY NOT NULL PRIMARY KEY";
                findSetter(m);
                continue;
            }
            switch (m.getter.getReturnType().getSimpleName()) {
                case "String":
                    m.sqlType = "VARCHAR(255)";
                    break;
                case "Integer":
                case "int":
                    m.sqlType = "INTEGER";
                    break;
                default:
                    m.sqlType = "UNKNOWN_" + m.getter.getReturnType().getSimpleName();
            }

            findSetter(m);
        }

        mappings.sort(Comparator.comparing(PropertyMapping::getName));

    }

    private void findSetter(PropertyMapping m) {
        String getterName = m.getter.getName();
        String setterName = "set" + getterName.substring(3);

        for (Method method : methods) {
            if (method.getName().equals(setterName)) {
                m.setter = method;
                break;
            }
        }
    }

    String getCreateTable() {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(getTableName()).append(" (");
        for (PropertyMapping mapping : mappings) {
            if (!first) {
                sb.append(", ");
            } else first = false;
            sb.append(mapping.name).append(' ').append(mapping.sqlType);
        }

        sb.append(")");
        return sb.toString();
    }

    String getDropTable() {
        return "DROP TABLE " + getTableName();
    }

    String getDropTableIfExists() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    String getInsert() {
        return "INSERT INTO " + getTableName() + " (" + String.join(", ", getColumnNames()) + ") VALUES (?, ?, ?)";
    }

    String getSelectById() {
        return "SELECT " + String.join(", ", getColumnNames()) + " FROM " + getTableName() + " WHERE ID=?";
    }

    String getSelectAll() {
        return "SELECT " + String.join(", ", getColumnNames()) + " FROM " + getTableName();
    }
}

class PropertyMapping {
    String name;
    String sqlType;
    Method getter;
    Method setter;

    Object getValue(Object t) {
        try {
            return getter.invoke(t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    <G extends DataSet> void setValue(G t, Object value) {
        try {
            setter.invoke(t,value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
}
