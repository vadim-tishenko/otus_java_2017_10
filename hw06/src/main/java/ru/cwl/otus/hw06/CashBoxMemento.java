package ru.cwl.otus.hw06;

/**
 * Created by tischenko on 11.12.2017 15:26.
 */
public class CashBoxMemento {
    final int max;
    final int banknotesCount;
    final int nominal;

    public CashBoxMemento(int max, int banknotesCount, int nominal) {
        this.max=max;
        this.banknotesCount=banknotesCount;
        this.nominal=nominal;
    }
}
