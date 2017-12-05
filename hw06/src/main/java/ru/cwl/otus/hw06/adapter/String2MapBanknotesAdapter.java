package ru.cwl.otus.hw06.adapter;

import ru.cwl.otus.hw06.MoneysPack;
import ru.cwl.otus.hw06.adapter.BanknotesAdapter;

import java.util.*;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 16:03.
 */
public class String2MapBanknotesAdapter implements BanknotesAdapter<String> {
    @Override
    public MoneysPack from(String banknotes) {
        String[] arr = banknotes.split(",");
        MoneysPack map = new MoneysPack();
        for (String banknote : arr) {
            final int nominal = Integer.parseInt(banknote);
            map.put(nominal, map.getOrDefault(nominal, 0) + 1);
        }
        return map;
    }

    @Override
    public String to(MoneysPack money) {

        final List<String> resList = new ArrayList<>();
        List<Integer> l = new ArrayList<>(money.keySet());
        Collections.sort(l);
        Collections.reverse(l);

        for (Integer nominal : l) {
            for (int i = 0; i < money.get(nominal); i++) {
                resList.add(nominal.toString());
            }
        }
        return String.join(",", resList);
    }
}
