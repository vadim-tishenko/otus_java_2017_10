package ru.cwl.otus.hw06.exception;

import ru.cwl.otus.hw06.exception.AtmException;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 13:59.
 */
public class NotEnoughSpaceException extends AtmException {
    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
