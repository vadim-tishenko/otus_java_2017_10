package ru.cwl.otus.hw09;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

/**
 * Created by vadim.tishenko
 * on 02.01.2018 13:42.
 * TABLE USERS
 * ID,NAME,AGE
 */

public class SQLGeneratorTest {
    SQLGenerator gen;

    @Before
    public void setUp() {
        gen = new SQLGenerator(UserDataSet.class);
    }

    @Test
    public void getTableName() {
        assertThat("USERS", is(gen.getTableName()));
    }

    @Test
    public void getColumnNames() {
        assertThat(Arrays.asList(gen.getColumnNames()), contains("AGE", "ID", "NAME"));
    }

    @Test
    public void getCreateTable() {
        String qq = "CREATE TABLE USERS (" +
                "AGE INTEGER, " +
                "ID BIGINT IDENTITY NOT NULL PRIMARY KEY, " +
                "NAME VARCHAR(255)" +
                ")";
        assertThat(gen.getCreateTable(), is(qq));
    }

    @Test
    public void getDropTable() {
        assertThat(gen.getDropTable(), is("DROP TABLE USERS"));
    }

    @Test
    public void getInsert() {
        assertThat(gen.getInsert(), is("INSERT INTO USERS (AGE, ID, NAME) VALUES (?, ?, ?)"));
    }

    @Test
    public void getSelectBiId() {
        assertThat(gen.getSelectById(), is("SELECT AGE, ID, NAME FROM USERS WHERE ID=?"));
    }

    @Test
    public void getSelectAll() {
        assertThat(gen.getSelectAll(), is("SELECT AGE, ID, NAME FROM USERS"));
    }

    @Test
    public void getDropTableIfExists() {
        assertThat(gen.getDropTableIfExists(), is("DROP TABLE IF EXISTS USERS"));
    }
}