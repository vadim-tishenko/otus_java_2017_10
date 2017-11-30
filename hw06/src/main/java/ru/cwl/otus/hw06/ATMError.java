package ru.cwl.otus.hw06;

/**
 * Created by tischenko on 30.11.2017 16:06.
 */
public class ATMError extends RuntimeException {
    public ATMError(String message) {
        super(message);
    }
}
