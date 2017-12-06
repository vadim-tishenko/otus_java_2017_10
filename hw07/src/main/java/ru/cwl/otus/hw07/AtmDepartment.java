package ru.cwl.otus.hw07;

import ru.cwl.otus.hw06.ATM;

import java.util.ArrayList;
import java.util.List;

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

    public AtmState getState() {
        return null;
    }

    public void restoreState(AtmState state) {

    }
}
