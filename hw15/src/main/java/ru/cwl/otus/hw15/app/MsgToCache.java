package ru.cwl.otus.hw15.app;

import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.Addressee;
import ru.cwl.otus.hw15.messageSystem.Message;

public abstract class MsgToCache extends Message {
    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheService) {
            exec((CacheService) addressee);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public abstract void exec(CacheService dbService);
}
