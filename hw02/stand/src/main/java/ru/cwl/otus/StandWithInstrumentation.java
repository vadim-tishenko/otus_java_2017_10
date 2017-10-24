package ru.cwl.otus;
/**
 * Created by vadim.tishenko
 * on 23.10.2017 22:18.
 */


import ru.cwl.hw02.stand.Stand;

import java.math.BigDecimal;
import java.util.*;

public class StandWithInstrumentation {
    public static void main(String[] args) {
        List<Integer> i1000 = Collections.nCopies(1_000, new Integer(1000));
        List<Integer> i1000000 = Collections.nCopies(1_000_000, new Integer(1000));
        printObjectSize(new Object());
        printObjectSize(new Integer(2001));
        printObjectSize(new Long(2001));
        printObjectSize("");
        printObjectSize(new ArrayList());
        printObjectSize(new ArrayList(i1000));
        printObjectSize(new ArrayList(i1000000));
        printObjectSize(new LinkedList());
        printObjectSize(new LinkedList(i1000));
        printObjectSize(new LinkedList(i1000000));
    }

    public static void printObjectSize(Object obj) {
        System.out.println(String.format("%s, size=%s", obj.getClass().getSimpleName(), Stand.sizeOf(obj)));
    }

}
