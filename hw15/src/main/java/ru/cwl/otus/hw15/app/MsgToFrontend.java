package ru.cwl.otus.hw15.app;


import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.Addressee;
import ru.cwl.otus.hw15.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public abstract void exec(FrontendService frontendService);
}