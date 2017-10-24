package ru.cwl.hw02.stand;

import ru.cwl.hw02.agent.MemoryAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by vadim.tishenko
 * on 24.10.2017 22:12.
 */
public class Stand {
    public static long sizeOf(Object obj) {
        long size = 0;
        Set<Object> processed = new HashSet<>();
        Queue<Object> q = new LinkedList<>();
        q.add(obj);

        Object o;
        while ((o = q.poll()) != null) {
            if (processed.contains(o)) continue;
            size += MemoryAgent.getSize(o);
            q.addAll(getChildObj(o));
            processed.add(o);
        }

        return size;
    }

    private static Collection<Object> getChildObj(Object obj) {
        List<Object> childObjects = new ArrayList<>();

        if (obj.getClass().isArray()) {
            if (obj.getClass().getName().startsWith("[L")) {
                return Arrays.asList((Object[]) obj);
            }
        }

        for (Class<?> clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {

            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                final Class<?> type = field.getType();

                if (type.isPrimitive()) {
                    continue;
                }

                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null) {
                        childObjects.add(value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return childObjects;
    }
}
