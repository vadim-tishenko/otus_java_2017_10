package ru.cwl.otus.hw06;

import java.util.HashMap;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 16:54.
 */
public class MoneysPack extends HashMap<Integer,Integer> {
    public int getSum(){
        return entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
    }
}
