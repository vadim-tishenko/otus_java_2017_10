package ru.cwl.otus.hw06.adapter;


import ru.cwl.otus.hw06.MoneysPack;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 16:05.
 */
public   interface  BanknotesAdapter<T> {
    MoneysPack from(T src);
    T to(MoneysPack map);
}
