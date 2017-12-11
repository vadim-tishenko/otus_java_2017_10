package ru.cwl.otus.hw06;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by vadim.tishenko
 * on 01.12.2017 0:30.
 */
public class CashBoxes {
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
            result += (cashBox.getFreeSpace()) * cashBox.getNominal();
        }
        return result;
    }
    List<CashBox>getCashBoxes(){
        ArrayList<CashBox> list = new ArrayList<>(cashBoxList);
        list.sort((c1, c2) -> c2.getNominal() - c1.getNominal());
        return list;
    }

    public List<CashBoxMemento> saveToMemento() {
        return cashBoxList.stream().map(CashBox::saveToMemento).collect(toList());
    }

    public void restoreFromMemento(List<CashBoxMemento> mementos) {
        Iterator<CashBox> iter = cashBoxList.iterator();
        for (CashBoxMemento memento : mementos) {
            iter.next().restoreFromMemento(memento);
        }
    }


}
