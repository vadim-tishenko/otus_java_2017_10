package ru.cwl.otus.hw06;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:40.
 */
public class ATM {

    CashBoxes cbs = new CashBoxes();
    CashIOController io = new CashIOController(cbs);

    void insert(CashBox cashBox) {
        cbs.insert(cashBox);
    }

    void remove(CashBox cashBox) {
        cbs.remove(cashBox);
    }

    int getBalance() {
        return cbs.getBalance();
    }

    public int putMoney(String banknotes) {
        return io.putMoney(banknotes);
    }

    public String getMoney(int i) {
        return io.getMoney(i);
    }


}
