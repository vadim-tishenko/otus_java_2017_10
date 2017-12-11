package ru.cwl.otus.hw07;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import ru.cwl.otus.hw06.ATM;
import ru.cwl.otus.hw06.CashBox;

/**
 * Created by vadim.tishenko
 * on 06.12.2017 22:15.
 */
public class AtmBuilder {
    private List<Pair<Integer, Integer>> cashBoxParams = new ArrayList<>();

    public AtmBuilder clear() {
        cashBoxParams.clear();
        return this;
    }

    public AtmBuilder cb(int banknotesCount, int nominal) {
        cashBoxParams.add(new Pair<>(banknotesCount, nominal));

        return this;
    }

    public ATM build() {
        ATM atm = new ATM();
        for (Pair<Integer, Integer> pair : cashBoxParams) {
            atm.insert(new CashBox(100, pair.getKey(), pair.getValue()));
        }
        return atm;
    }
}
