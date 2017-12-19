package ru.cwl.otus.hw09;

/**
 * Created by vadim.tishenko
 * on 17.12.2017 12:48.
 */
public class UserDataSet extends DataSet {
    String name;
    int age;

    @Override
    public String toString() {
        return "UserDataSet{" + "id=" + id +
                ",name='" + name + '\'' +
                ", age=" + age +

                '}';
    }
}
