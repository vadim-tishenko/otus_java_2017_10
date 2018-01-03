package ru.cwl.otus.hw09;


import sun.reflect.misc.FieldUtil;
import sun.reflect.misc.MethodUtil;

import java.lang.reflect.Field;
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
    Field[] fields;
    Method[] methods;
    List<FieldMapping> mappings = new ArrayList<>();

    public SQLGenerator(Class<? extends DataSet> userDataSetClass) {
        clazz = userDataSetClass;
        fields = FieldUtil.getDeclaredFields(clazz);
        methods = MethodUtil.getMethods(clazz);
        refreshColumnMapping();
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
        for (FieldMapping mapping : mappings) {
            methodList.add(mapping.name);
        }

        return methodList.toArray(new String[]{});
    }

    void refreshColumnMapping() {

        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                if (method.getName().equals("getClass")) continue;
                final String fieldName = method.getName().substring(3).toUpperCase();
                FieldMapping fm = new FieldMapping();
                fm.name = fieldName;
                fm.getter = method;
                mappings.add(fm);
            }
        }

        for (FieldMapping m : mappings) {
            if (m.name.equals("ID")) {
                m.sqlType = "BIGINT IDENTITY NOT NULL PRIMARY KEY";
                findSetter(m);
                continue;
            }
            switch (m.getter.getReturnType().getSimpleName()){
                case "String":
                    m.sqlType="VARCHAR(255)";
                    break;
                case "Integer":
                case "int":
                    m.sqlType="INTEGER";
                    break;
                default:
                    m.sqlType="UNKNOWN_"+m.getter.getReturnType().getSimpleName();
            }

            findSetter(m);
        }

        mappings.sort(Comparator.comparing(FieldMapping::getName));

    }

    private void findSetter(FieldMapping m) {
        String getterName = m.getter.getName();
        String setterName = "set" + getterName.substring(3);

        for (Method method : methods) {
            if(method.getName().equals(setterName)){
                m.setter=method;
                break;
            }
        }
    }

    String getCreateTable() {
/*        String qq = "CREATE TABLE USERS (" +
                "AGE INTEGER, " +
                "ID BIGINT IDENTITY NOT NULL PRIMARY KEY, " +
                "NAME VARCHAR(255)" +
                " )";
        String z = "CREATE TABLE USERS\n" +
                "(\n" +
                "  ID   BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_CB481AEE_15B5_4973_8B68_DDE50FC8D42C) AUTO_INCREMENT NOT NULL,\n" +
                "  NAME VARCHAR(255),\n" +
                "  AGE  INTEGER\n" +
                ")";*/

        boolean first=true;
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(getTableName()).append(" (");
        for (FieldMapping mapping : mappings) {
            if(!first){ sb.append(", ");}else first=false;
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

class FieldMapping {
    String name;
    String sqlType;
    Method getter;
    Method setter;

    public String getName() {
        return name;
    }
}
