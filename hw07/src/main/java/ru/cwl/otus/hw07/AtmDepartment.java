package ru.cwl.otus.hw07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ru.cwl.otus.hw06.ATM;
import ru.cwl.otus.hw06.AtmMemento;

import static java.util.stream.Collectors.toList;

/**
 * Created by vadim.tishenko
 * on 05.12.2017 21:44.
 */
public class AtmDepartment {
    private List<ATM> atmList=new ArrayList<>();
    public int getBalance(){
        int result=0;
        for (ATM atm : atmList) {
            result+=atm.getBalance();
        }
        return result;
    }

    void add(ATM... atms){
        Collections.addAll(atmList, atms);
    }

    AtmDepartmentMemento saveToMemento() {
        return new AtmDepartmentMemento(atmList.stream().map(ATM::saveToMemento).collect(toList()));
    }

    void restoreFromMemento(AtmDepartmentMemento memento) {
        Iterator<ATM> atmIterator = atmList.iterator();
        for (AtmMemento atmMemento : memento.atmMementos) {
            atmIterator.next().restoreFromMemento(atmMemento);

        }
    }
}
