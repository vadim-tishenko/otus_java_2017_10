package ru.cwl.otus.hw06.adapter;

import ru.cwl.otus.hw06.MoneysPack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 16:09.
 */
public class List2BanknotesAdapter implements BanknotesAdapter<List<Integer>> {

    @Override
    public MoneysPack from(List<Integer> src) {
        MoneysPack result=new MoneysPack();

        for (Integer banknote : src) {
            result.put(banknote, result.getOrDefault(banknote, 0) + 1);
        }

        return result;
    }

    @Override
    public List<Integer> to(MoneysPack map) {

        final List<Integer> resList = new ArrayList<>();
        List<Integer> l = new ArrayList<>(map.keySet());
        Collections.sort(l);
        Collections.reverse(l);

        for (Integer nominal : l) {
            for (int i = 0; i < map.get(nominal); i++) {
                resList.add(nominal);
            }
        }

        return resList;
    }
}
