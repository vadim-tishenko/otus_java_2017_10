package ru.cwl.otus.hw06.exception;

import ru.cwl.otus.hw06.exception.AtmException;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 13:59.
 */
public class NotEnoughMoneyException extends AtmException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
