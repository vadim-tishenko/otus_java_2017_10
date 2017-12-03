package ru.cwl.otus.hw06;

import ru.cwl.otus.hw06.exception.NotEnoughMoneyException;
import ru.cwl.otus.hw06.exception.NotEnoughSpaceException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadim.tishenko
 * on 01.12.2017 0:29.
 */
public class CashIOController {
    private CashBoxes cbs;

    public CashIOController(CashBoxes cb) {
        this.cbs = cb;
    }

    public int putMoney(MoneysPack moneysPack) {

        int sum = moneysPack.getSum();

        if (sum > cbs.freeSpace()) {
            throw new NotEnoughSpaceException("не хватает места под деньги");
        }

        List<CashBox> list = cbs.getCashBoxes();

        Map<CashBox, Integer> res = new LinkedHashMap<>();

        for (CashBox cb : list) {
            int n0 = moneysPack.getOrDefault(cb.getNominal(), 0);
            if (n0 == 0) continue;
            int n = Integer.min(n0, cb.getFreeSpace());
            if (n != 0) {
                res.put(cb, n);
                moneysPack.put(cb.getNominal(), n0 - n);
            }
        }

        int sum1 = moneysPack.getSum();
        if (sum1 > 0) {
            throw new NotEnoughSpaceException("не хватает места под деньги 2");
        }

        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().add(entry.getValue());
        }
        return sum;

    }

    public MoneysPack getMoney(int count) {
        if (cbs.getBalance() < count) {
            throw new NotEnoughMoneyException("не хватает денег для выдачи");
        }
        List<CashBox> list = cbs.getCashBoxes();
        Map<CashBox, Integer> res = new LinkedHashMap<>();
        for (CashBox cb : list) {
            int nCount = count / cb.getNominal();
            int n = Integer.min(nCount, cb.getCount());
            if (n != 0) {
                res.put(cb, n);
                count = count - n * cb.getNominal();
            }
        }
        if (count != 0) {
            throw new NotEnoughMoneyException("не хватает денег для выдачи 2");
        }

        MoneysPack result = new MoneysPack();
        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            final Integer moneyValue = entry.getValue();
            final int nominal = entry.getKey().getNominal();

            entry.getKey().sub(moneyValue);
            result.put(nominal, result.getOrDefault(nominal, 0) + moneyValue);

        }
        return result;
    }
}
