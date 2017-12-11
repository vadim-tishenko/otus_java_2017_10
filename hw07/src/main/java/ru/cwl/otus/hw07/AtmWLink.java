package ru.cwl.otus.hw07;

import ru.cwl.otus.hw06.ATM;

/**
 * Created by vadim.tishenko
 * on 11.12.2017 19:24.
 */
public class AtmWLink extends ATM {
    AtmWLink next= NOP;

    static AtmWLink NOP = new AtmWLink() {
        @Override
        public int getBalance() {
            return 0;
        }

        @Override
        public AtmMementoWLink saveToMemento() {
            return null;
        }

        @Override
        public void restoreFromMemento(AtmMementoWLink memento) {
        }
    };

    @Override
    public int getBalance() {
        return super.getBalance() + next.getBalance();
    }

    @Override
    public AtmMementoWLink saveToMemento() {
        return new AtmMementoWLink(cbs.saveToMemento(), next.saveToMemento());
    }

    public void restoreFromMemento(AtmMementoWLink memento) {
        super.restoreFromMemento(memento);
        next.restoreFromMemento(memento.next);
    }
}
