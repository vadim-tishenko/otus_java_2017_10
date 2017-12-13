package ru.cwl.otus.hw08;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by tischenko on 13.12.2017 13:15.
 */
public class JsonSerializer {
    String toJson(Object object) {
        Objects.nonNull(object);
        Class<? extends Object> clazz = object.getClass();
        StringBuilder sb = new StringBuilder();
        List<String> res1 = new ArrayList<>();
        sb.append('{');
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value == null) {
                res1.add('"' + field.getName() + '"' + ":" + value);
                continue;
            }
            if (value.getClass().isArray()) {
                // []

                int len = Array.getLength(value);
                for(int i=0;i<len;i++){
                    Object aVal = Array.get(value, i);
                }
                res1.add('"' + field.getName() + '"' + ":" +writeArray(value));
                continue;
            }
            if (value instanceof Map) {

                //  {}
                Map map= (Map) value;
                res1.add('"' + field.getName() + '"' + ":" +writeMap(map));

                continue;
            }
            if (value instanceof Collection) {
                // []
                Collection collection= (Collection) value;
                res1.add('"' + field.getName() + '"' + ":" +writeCollection(collection));

                continue;
            }
            res1.add('"' + field.getName() + '"' + ":" + value);
        }
        sb.append(String.join(",", res1));
        sb.append('}');
        return sb.toString();
    }

    private String writeCollection(Collection collection) {
        return null;
    }

    private String writeMap(Map map) {
        return null;
    }

    private String writeArray(Object value) {
        return null;
    }
}
