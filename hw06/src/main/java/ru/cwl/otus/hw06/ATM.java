package ru.cwl.otus.hw06;

import ru.cwl.otus.hw06.adapter.BanknotesAdapter;
import ru.cwl.otus.hw06.adapter.String2MapBanknotesAdapter;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:40.
 */
public class ATM {

    CashBoxes cbs = new CashBoxes();
    CashIOController io = new CashIOController(cbs);
    BanknotesAdapter<String> adapter=new String2MapBanknotesAdapter();

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
        return io.putMoney(adapter.from(banknotes));
    }

    public String getMoney(int count) {
        return adapter.to(io.getMoney(count));
    }


}
