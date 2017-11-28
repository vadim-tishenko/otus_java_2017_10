package ru.cwl.otus.hw06;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:31.
 */
public class CashBox {
    final int max;
    int banknotesCount;
    final int nominal;

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

    public int getBalance(){
        return nominal*banknotesCount;
    }

    public int getCount(){
        return banknotesCount;
    }
}
