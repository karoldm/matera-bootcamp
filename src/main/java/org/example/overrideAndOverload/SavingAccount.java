package org.example.overrideAndOverload;

import lombok.Data;

public class SavingAccount extends Account {
    public SavingAccount(Double balance) {
        super(balance);
    }

    // example of override
    @Override
    public void deposit(Double amount) {
        this.balance += amount;
    }

    // example of overload
    public void withdraw(Double amount) {
        this.balance -= amount;
    }

    public void withdraw(Double amount, Double tax){
        this.balance -= amount + tax;
    }
}
