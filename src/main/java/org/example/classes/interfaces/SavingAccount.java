package org.example.classes.interfaces;

public class SavingAccount implements Account{
    Double balance = 0.0;

    @Override
    public void deposit(Double value) {
        this.balance += value;
    }

    @Override
    public void withdraw(Double value) {
        this.balance -= value;
    }

    @Override
    public Double getBalance() {
        return this.balance;
    }
}
