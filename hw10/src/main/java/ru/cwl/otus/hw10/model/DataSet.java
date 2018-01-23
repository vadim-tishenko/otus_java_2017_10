package ru.cwl.otus.hw10.model;

import javax.persistence.*;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 17:27.
 */
@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
