package ru.cwl.otus.hw07;

/**
 * Created by tischenko on 11.12.2017 16:50.
 */
public class AtmDepartmentWithCoR {
    private AtmWLink first= AtmWLink.NOP;

    public int getBalance(){
        return first.getBalance();
    }

    void add(AtmWLink... atms){
        for (AtmWLink atm : atms) {
            atm.next=first;
            first=atm;
        }
    }

    AtmMementoWLink saveToMemento() {
        return first.saveToMemento();
    }

    void restoreFromMemento(AtmMementoWLink memento) {
        first.restoreFromMemento(memento);
    }

}
