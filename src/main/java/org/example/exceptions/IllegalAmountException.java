package org.example.exceptions;

public class IllegalAmountException extends Exception {
    public IllegalAmountException(Double amount) {
        super("The value must be higher than zero: " + amount);
    }
}
