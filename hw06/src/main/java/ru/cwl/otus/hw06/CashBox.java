package ru.cwl.otus.hw06;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:31.
 */
public class CashBox {
    private int max;
    private int banknotesCount;
    private int nominal;

    public CashBox(int max, int banknotesCount, int nominal) {
        this.max = max;
        this.banknotesCount = banknotesCount;
        this.nominal = nominal;
    }

    public void add(int numBanknotes) {
        if (banknotesCount + numBanknotes <= max) {
            banknotesCount += numBanknotes;
        }
    }

    public void sub(int numBanknotes) {
        if (banknotesCount - numBanknotes >= 0) {
            banknotesCount -= numBanknotes;
        }
    }

    public int getBalance() {
        return nominal * banknotesCount;
    }

    public int getCount() {
        return banknotesCount;
    }

    public int getFreeSpace() {
        return max - banknotesCount;
    }

    public int getNominal() {
        return nominal;
    }

    CashBoxMemento saveToMemento() {
        return new CashBoxMemento(max, banknotesCount, nominal);
    }

    void restoreFromMemento(CashBoxMemento memento) {
        max = memento.max;
        banknotesCount = memento.banknotesCount;
        nominal = memento.nominal;
    }
}
