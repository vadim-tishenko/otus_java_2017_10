package ru.cwl.otus.hw07;

import ru.cwl.otus.hw06.ATM;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Created by vadim.tishenko
 * on 05.12.2017 21:44.
 */
public class AtmDepartment {
    List<ATM> atmList=new ArrayList<>();
    public int getBalance(){
        int result=0;
        for (ATM atm : atmList) {
            result+=atm.getBalance();
        }
        return result;
    }
    void add(ATM atm){
        atmList.add(atm);
    }

    void add(ATM... atms){
        for (ATM atm : atms) {
            atmList.add(atm);
        }
    }

    String saveToMemento() {
        return atmList.stream().map(ATM::saveToMemento).collect(joining("\n"));
    }

    void restoreFromMemento(String memento) {
        String[] values = memento.split("\\n");
        for (int i=0;i<values.length;i++) {
            atmList.get(i).restoreFromMemento(values[i]);
        }
    }
}
