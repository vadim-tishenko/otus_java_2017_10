package ru.cwl.otus.hw15.system;

import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.Addressee;
import ru.cwl.otus.hw15.messageSystem.MessageSystem;

/**
 * Created by vadim.tishenko
 * on 10.03.2018 18:58.
 */
abstract public class AbstractAddressee implements Addressee {

    final private Address address;
    final private MessageSystem messageSystem;

    public AbstractAddressee(Address address, MessageSystem messageSystem) {
        this.address = address;
        this.messageSystem = messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystem;
    }
}
