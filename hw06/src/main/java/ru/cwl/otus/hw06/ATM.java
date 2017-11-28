package ru.cwl.otus.hw06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:40.
 */
public class ATM {
    List<CashBox> cashBoxList = new ArrayList<>();

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
        return "";
    }
}
