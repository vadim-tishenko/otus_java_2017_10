package ru.cwl.otus.hw07;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ru.cwl.otus.hw06.ATM;
import ru.cwl.otus.hw06.AtmMemento;

import static java.util.stream.Collectors.toList;

/**
 * Created by tischenko on 11.12.2017 16:50.
 */
public class AtmDepatmentWithCoR {

}

class Handler {
    Handler next;
    ATM atm;

    public int getBalance() {
        return atm.getBalance() + next.getBalance();
    }

    void saveToMemento(List<AtmMemento> mementos) {
        mementos.add(atm.saveToMemento());
        next.saveToMemento(mementos);
    }

    void restoreFromMemento(List<AtmMemento> mementos) {
        atm.restoreFromMemento(mementos.get(0));
        mementos.remove(0);
        next.restoreFromMemento(mementos);
    }


}
class Handler0 extends Handler{
    @Override
    public int getBalance() {
        return 0;
    }

    @Override
    void saveToMemento(List<AtmMemento> mementos) {
    }

    @Override
    void restoreFromMemento(List<AtmMemento> mementos) {
    }
}
