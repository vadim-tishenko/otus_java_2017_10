package ru.cwl.otus.hw08;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by vadim.tishenko
 * on 14.12.2017 23:00.
 */
enum WriteFieldValueStrategy {
    SIMPLE(o -> o == null || o instanceof Number || o instanceof Boolean, String::valueOf),
    // TODO: 14.12.2017 add escaping
    STR(o -> o instanceof String, o -> "\"" + o + "\""),

    //MAP(o -> o instanceof Map, o -> writeMap(o)),

    DEFAULT {

    };


    WriteFieldValueStrategy() {
    }

    WriteFieldValueStrategy(Predicate<Object> predicate, Function<Object, String> func) {
        this(predicate);
        this.func = func;
    }

    WriteFieldValueStrategy(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    boolean test(Object o) {
        return predicate.test(o);
    }

    String write(Object o) {
        return func.apply(o);
    }

    protected Predicate<Object> predicate = o -> false;
    protected Function<Object, String> func = String::valueOf;


}
