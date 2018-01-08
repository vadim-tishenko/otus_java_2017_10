package ru.cwl.otus.hw10;

import ru.cwl.otus.hw10.model.DataSet;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 14:54.
 */
public interface DBService {
    <T extends DataSet> void save(T user);

    <T extends DataSet> T load(long id, Class<T> clazz);

    <T extends DataSet> List<T> load(Class<T> clazz);

    void close();
}
