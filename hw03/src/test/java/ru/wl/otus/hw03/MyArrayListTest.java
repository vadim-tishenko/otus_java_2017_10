package ru.wl.otus.hw03;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * Created by vadim.tishenko
 * on 28.10.2017 21:57.
 */
public class MyArrayListTest {

    @Test
    public void size() throws Exception {
        List<String> actual = Arrays.asList("a", "b", "c");
        MyArrayList<String> l = new MyArrayList<String>();
        assertThat(l,hasSize(0));
        l.addAll(actual);
        assertThat(l,hasSize(3));
        l.remove("a");
        assertThat(l,hasSize(2));
    }

}