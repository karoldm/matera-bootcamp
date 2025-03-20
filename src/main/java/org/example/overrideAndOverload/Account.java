package org.example.overrideAndOverload;

import lombok.Data;

@Data
public class Account {
    protected Double balance;
    private final Double tax = 0.45;

    public Account(Double balance) {
        this.balance = balance;
    }

    public void deposit(Double amount) {
        this.balance += amount - tax;
    }
}
