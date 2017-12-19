package ru.cwl.otus.hw08;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by tischenko on 13.12.2017 13:15.
 */
public class JsonSerializer {
    String toJson(Object object) {
        Objects.requireNonNull(object);
        Class<?> clazz = object.getClass();
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        sb.append('{');
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            final String fieldName = String.format("\"%s\":", field.getName());

            result.add(fieldName + write(value));
        }
        sb.append(String.join(",", result));
        sb.append('}');
        return sb.toString();
    }

    private String write(Object value) {
        for (WriteFieldValueStrategy writeStrategy : WriteFieldValueStrategy.values()) {
            if (writeStrategy.test(value)) {
                return writeStrategy.write(value);
            }
        }

        if (value.getClass().isArray()) {

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            int len = Array.getLength(value);
            for (int i = 0; i < len; i++) {
                Object aVal = Array.get(value, i);
                if (sb.length() != 1) sb.append(',');
                sb.append(write(aVal));
            }
            sb.append(']');
            return sb.toString();
        }

        if (value instanceof Collection) {
            Collection c = (Collection) value;
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (Object aVal : c) {
                if (sb.length() != 1) sb.append(',');
                sb.append(write(aVal));
            }
            sb.append(']');
            return sb.toString();
        }

        return toJson(value);
    }

}

