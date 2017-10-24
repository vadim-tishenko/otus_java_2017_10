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
        List<Integer> i100 = Collections.nCopies(100, new Integer(1000));
        List<Integer> i1000 = Collections.nCopies(1_000, new Integer(1000));
        List<Integer> i1000000 = Collections.nCopies(1_000_000, new Integer(1000));
        printObjectSize("");
        printObjectSize(new ArrayList());
        printObjectSize(new ArrayList(i1000));
        printObjectSize(new ArrayList(i1000000));
        printObjectSize(new LinkedList());
        printObjectSize(new LinkedList(i1000));
        printObjectSize(new LinkedList(i1000000));
        printObjectSize(new HashSet());

        printObjectSize(new Integer(2001));
        printObjectSize(new Long(2001));
        /*printObjectSize(new Object());
        printObjectSize(new A());
        printObjectSize(new B());
        printObjectSize(new E());
        printObjectSize(new F());
        printObjectSize(new G());
        printObjectSize(new Integer(2001));
        printObjectSize(new Long(2001));

        printObjectSize("qwertyuiop");
        printObjectSize(1);
        printObjectSize("string");
        printObjectSize("");
        printObjectSize(Calendar.getInstance());
        printObjectSize(new BigDecimal("999999999999999.999"));
        printObjectSize(new ArrayList<String>());
        printObjectSize(new Integer[100]);
        printObjectSize(new int[1000]);
        printObjectSize(new Integer[1000]);
        printObjectSize(new long[1000]);
        printObjectSize(new Long[1000]);
*/

        Long[] l0 = new Long[3];
        printObjectSize(l0);
        Long[] ll = new Long[]{1000L, 1001L, 1002L};
        printObjectSize(ll);
        printObjectSize(new H());


    }

    public static void printObjectSize(Object obj) {
        System.out.println(String.format("%s, size=%s", obj.getClass().getSimpleName(), Stand.sizeOf(obj)));
    }

}

class A {
    Integer id = 1000;
    String name;
    long l;
    long d;
    int[] intArray = new int[100];
    Integer[] intArray2 = new Integer[100];
    List<String> lstString = new ArrayList<>();
}

class B extends A {
    long e;
    String s = "qwertyuiop";
}

class E {
    long a;

}

class F extends E {
    long b;

}

class G extends E {
    Integer c = 2000;
}

class H {
    int a[] = new int[100];
}