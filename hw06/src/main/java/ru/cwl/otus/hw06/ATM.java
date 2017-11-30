package ru.cwl.otus.hw06;

import java.util.*;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:40.
 */
public class ATM {

    private List<CashBox> cashBoxList = new ArrayList<>();

    void insert(CashBox cashBox) {
        cashBoxList.add(cashBox);
    }

    void remove(CashBox cashBox) {
        cashBoxList.remove(cashBox);
    }

    int getBalance() {
        int result = 0;
        for (CashBox cashBox : cashBoxList) {
            result += cashBox.getBalance();
        }
        return result;
    }

    int freeSpace() {
        int result = 0;
        for (CashBox cashBox : cashBoxList) {
            result += (cashBox.max - cashBox.getCount()) * cashBox.nominal;
        }
        return result;
    }

    public int putMoney(String banknotes) {
        String[] arr = banknotes.split(",");
        Map<Integer, Integer> map = new HashMap<>();
        for (String banknote : arr) {
            final int nominal = Integer.parseInt(banknote);
            map.put(nominal, map.getOrDefault(nominal, 0) + 1);
        }
        int sum = getBanknotesSum(map);

        if (sum > freeSpace()) {
            throw new ATMError("не хватает места под деньги");
        }

        ArrayList<CashBox> list = new ArrayList<>(cashBoxList);
        list.sort((c1, c2) -> c2.nominal - c1.nominal);

        Map<CashBox, Integer> res = new LinkedHashMap<>();

        for (CashBox cb : list) {
            int n0 = map.getOrDefault(cb.nominal, 0);
            if (n0 == 0) continue;
            int n = Integer.min(n0, cb.max - cb.banknotesCount);
            if (n != 0) {
                res.put(cb, n);
                map.put(cb.nominal, n0 - n);
            }
        }

        int sum1 = getBanknotesSum(map);
        if (sum1 > 0) {
            throw new ATMError("не хватает места под деньги 2");
        }

        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().add(entry.getValue());
        }
        return sum;

    }

    private int getBanknotesSum(Map<Integer, Integer> map) {
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }

    public String getMoney(int i) {
        if (getBalance() < i) {
            throw new ATMError("не хватает денег для выдачи");
        }
        ArrayList<CashBox> list = new ArrayList<>(cashBoxList);
        list.sort((c1, c2) -> c2.nominal - c1.nominal);
        Map<CashBox, Integer> res = new LinkedHashMap<>();
        for (CashBox cb : list) {
            int nCount = i / cb.nominal;
            int n = Integer.min(nCount, cb.banknotesCount);
            if (n != 0) {
                res.put(cb, n);
                i = i - n * cb.nominal;
            }
        }
        if (i != 0) {
            throw new ATMError("не хватает денег для выдачи 2");
        }

        List<String> resList = new ArrayList<String>();
        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().sub(entry.getValue());
            String v = String.valueOf(entry.getKey().nominal);
            for (int j = 0; j < entry.getValue(); j++) {
                resList.add(v);
            }

        }
        return String.join(",", resList);
    }
}
