package ru.cwl.otus.hw09;

/**
 * Created by vadim.tishenko
 * on 17.12.2017 12:48.
 */
public class UserDataSet extends DataSet {
    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" + "id=" + id +
                ",name='" + name + '\'' +
                ", age=" + age +

                '}';
    }
}
