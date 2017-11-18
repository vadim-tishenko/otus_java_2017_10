package ru.cwl.otus.hw05.tests;

import ru.cwl.otus.hw05.xxunit.Assert;
import ru.cwl.otus.hw05.xxunit.annotation.After;
import ru.cwl.otus.hw05.xxunit.annotation.Before;
import ru.cwl.otus.hw05.xxunit.annotation.Test;

/**
 * Created by vadim.tishenko
 * on 11.11.2017 18:46.
 */
public class TestClass2 {
    @Before
    public void met1(){
        System.out.println("BEFORE");
    }
    @Test
    public void test1(){
        System.out.println("TEST1");
    }
    @Test
    public void test2(){
        System.out.println("TEST2");
        String actual = "qw" + "er";
        Assert.assertEquals("qwer",actual);
    }
    @After
    public void met2(){
        System.out.println("AFTER");
    }

}
