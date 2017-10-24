package ru.cwl.otus; /**
 * Created by vadim.tishenko
 * on 23.10.2017 22:18.
 */


import ru.cwl.hw02.agent.MemoryAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StandWithInstrumentation {
    public static void main(String[] args) {
//        printObjectSize(new Object());
        printObjectSize(new A());
       /* printObjectSize(1);
        printObjectSize("string");
        printObjectSize(Calendar.getInstance());
        printObjectSize(new BigDecimal("999999999999999.999"));
        printObjectSize(new ArrayList<String>());
        printObjectSize(new Integer[100]);*/

    }

    public static void printObjectSize(Object obj) {


        System.out.println(String.format("%s, size=%s", obj.getClass().getSimpleName(), MemoryAgent.getSize(obj)));

        System.out.println("-- fields --");
        for (Field field : obj.getClass().getDeclaredFields()) {
            // +не статичесткие
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            System.out.println("-");
            //field.getGenericType()
            // если это ссылка != null, идем по ней. не забываем про зацыкливание.
            final Class<?> type = field.getType();
            boolean isArray = type.isArray();
            boolean isObject = !type.isPrimitive();
            String name = type.getName();


            System.out.printf("name: %s, array: %s obj: %s\n", name, isArray, isObject);

            System.out.printf("%s %s\n", field.getName(), field.getGenericType().getTypeName());
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                System.out.printf("v: %s o: %s\n", value, value instanceof Object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("----");

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
