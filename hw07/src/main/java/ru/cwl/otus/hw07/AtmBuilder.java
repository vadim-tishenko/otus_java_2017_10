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
    List<Pair<Integer, Integer>> sss = new ArrayList<>();

   /* public static AtmBuilder atm() {
        return new AtmBuilder();
    }*/

    public AtmBuilder clear() {
        sss.clear();
        return this;
    }

    public AtmBuilder cb(int banknotesCount, int nominal) {
        sss.add(new Pair<>(banknotesCount, nominal));

        return this;
    }

    public ATM build() {
        ATM atm = new ATM();
        for (Pair<Integer, Integer> ss : sss) {
            atm.insert(new CashBox(100, ss.getKey(), ss.getValue()));
        }
        return atm;
    }
}
