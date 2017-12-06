package ru.cwl.otus.hw07;

import ru.cwl.otus.hw06.ATM;
import ru.cwl.otus.hw06.CashBox;

/**
 * Created by vadim.tishenko
 * on 06.12.2017 22:15.
 */
public class AtmBuilder {
    private ATM atm=new ATM();
    public static AtmBuilder  atm(){
        return new AtmBuilder();
    }
    public AtmBuilder cb(int banknotesCount,int nominal){
        atm.insert(new CashBox(100,banknotesCount,nominal));
        return this;
    }
    public ATM build(){
        return atm;
    }
}
