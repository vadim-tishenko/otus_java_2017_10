package ru.cwl.otus.hw07;

import javafx.util.Pair;
import ru.cwl.otus.hw06.CashBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 11.12.2017 19:38.
 */
public class AtmBuilder2  {

    protected List<Pair<Integer, Integer>> cashBoxParams = new ArrayList<>();

    public AtmBuilder2 clear() {
        cashBoxParams.clear();
        return this;
    }

    public AtmBuilder2 cb(int banknotesCount, int nominal) {
        cashBoxParams.add(new Pair<>(banknotesCount, nominal));

        return this;
    }

    public AtmWLink build() {
        AtmWLink atm = new AtmWLink();
        for (Pair<Integer, Integer> pair : cashBoxParams) {
            atm.insert(new CashBox(100, pair.getKey(), pair.getValue()));
        }
        return atm;
    }
}