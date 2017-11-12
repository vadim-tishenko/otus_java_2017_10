package ru.cwl.otus.hw05;

import ru.cwl.otus.hw05.annotation.After;
import ru.cwl.otus.hw05.annotation.Before;
import ru.cwl.otus.hw05.annotation.MyAnn;
import ru.cwl.otus.hw05.annotation.Test;

/**
 * Created by vadim.tishenko
 * on 11.11.2017 18:46.
 */
@MyAnn
public class TestClass {
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
    }
    @After
    public void met2(){
        System.out.println("AFTER");
    }

}
