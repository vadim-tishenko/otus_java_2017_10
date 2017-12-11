package ru.cwl.otus.hw07;

import java.util.List;

import ru.cwl.otus.hw06.AtmMemento; /**
 * Created by tischenko on 11.12.2017 15:42.
 */
public class AtmDepartmentMemento {
    final List<AtmMemento> atmMementos;
    public AtmDepartmentMemento(List<AtmMemento> atmMementos){
        this.atmMementos =atmMementos;
    }
}
