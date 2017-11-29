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

    public void putMoney(String s) {
        String[] arr = s.split(",");
        Map<String, Integer> map = new HashMap<>();
        for (String s1 : arr) {
            map.put(s1, map.getOrDefault(s1, 0) + 1);
        }
        // точно поместятся.....
        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            for (CashBox cashBox : cashBoxList) {
                if (cashBox.nominal == Integer.parseInt(stringIntegerEntry.getKey())) {
                    cashBox.add(stringIntegerEntry.getValue());
                }
            }
        }

    }

    public String getMoney(int i) {
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
        List<String> resList = new ArrayList<String>();
        for (Map.Entry<CashBox, Integer> entry : res.entrySet()) {
            entry.getKey().sub(entry.getValue());
            String v = String.valueOf(entry.getKey().nominal);
            for(int j=0;j<entry.getValue();j++){
                resList.add(v);
            }

        }
        return String.join(",",resList);
    }
}
