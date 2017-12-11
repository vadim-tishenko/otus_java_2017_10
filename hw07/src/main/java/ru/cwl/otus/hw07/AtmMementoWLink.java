package ru.cwl.otus.hw07;

import ru.cwl.otus.hw06.AtmMemento;
import ru.cwl.otus.hw06.CashBoxMemento;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 11.12.2017 20:13.
 */
public class AtmMementoWLink extends AtmMemento {
    AtmMementoWLink next;

    public AtmMementoWLink(List<CashBoxMemento> cashBoxMementos, AtmMementoWLink next) {
        super(cashBoxMementos);
        this.next = next;
    }
}
