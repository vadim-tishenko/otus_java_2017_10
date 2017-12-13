package ru.cwl.otus.hw08;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tischenko on 13.12.2017 13:15.
 */
public class JsonSerializer {
    String toJson(Object object){
        Objects.nonNull(object);
        Class<? extends Object> clazz = object.getClass();
        StringBuilder sb=new StringBuilder();
        List<String> res1=new ArrayList<>();
        sb.append('{');
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Object value=null;
            try {
                value=field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            res1.add('"'+field.getName()+'"'+":"+value);
        }
        sb.append(String.join(",",res1));
        sb.append('}');
        return sb.toString();
    }
}
