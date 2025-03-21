package org.example.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(Double balance, Double amount) {
        super("Insufficient balance to withdraw (tax included) " + amount + ". Your balance is " + balance);
    }
}
