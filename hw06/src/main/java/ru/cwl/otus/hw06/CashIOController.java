package ru.cwl.otus.hw06;

import java.util.*;

/**
 * Created by vadim.tishenko
 * on 01.12.2017 0:29.
 */
public class CashIOController {
    private CashBoxes cbs;

    public CashIOController(CashBoxes cb) {
        this.cbs = cb;
    }

    public int putMoney(String banknotes) {
        String[] arr = banknotes.split(",");
        Map<Integer, Integer> map = new HashMap<>();
        for (String banknote : arr) {
            final int nominal = Integer.parseInt(banknote);
            map.put(nominal, map.getOrDefault(nominal, 0) + 1);
        }
        int sum = getBanknotesSum(map);

        if (sum > cbs.freeSpace()) {
            throw new NotEnoughSpaceException("не хватает места под деньги");
        }

        List<CashBox> list = cbs.getCashBoxes();

        Map<CashBox, Integer> res = new LinkedHashMap<>();

        for (CashBox cb : list) {
            int n0 = map.getOrDefault(cb.getNominal(), 0);
            if (n0 == 0) continue;
            int n = Integer.min(n0, cb.getFreeSpace());
            if (n != 0) {
                res.put(cb, n);
                map.put(cb.getNominal(), n0 - n);
            }
        }

        int sum1 = getBanknotesSum(map);
        if (sum1 > 0) {
            throw new NotEnoughSpaceException("не хватает места под деньги 2");
        }

        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().add(entry.getValue());
        }
        return sum;

    }

    private int getBanknotesSum(Map<Integer, Integer> map) {
        return map.entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
    }

    public String getMoney(int count) {
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

        List<String> resList = new ArrayList<String>();
        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().sub(entry.getValue());
            String v = String.valueOf(entry.getKey().getNominal());
            for (int j = 0; j < entry.getValue(); j++) {
                resList.add(v);
            }

        }
        return String.join(",", resList);
    }
}
