package ru.cwl.otus.hw11;

import ru.cwl.otus.hw10.model.DataSet;

import java.util.Objects;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 20:38.
 */
public class Key {
    Long id;
    Class clazz;

    public Key(Long id, Class clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    static Key of(DataSet entity){
        return new Key(entity.getId(),entity.getClass());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(id, key.id) &&
                Objects.equals(clazz, key.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clazz);
    }
}
