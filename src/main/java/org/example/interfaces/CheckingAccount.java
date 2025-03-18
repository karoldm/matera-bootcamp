package org.example.interfaces;

public class CheckingAccount implements  Account{
    private Double balance = 0.0;
    private final Double tax = 0.6;

    @Override
    public void deposit(Double value) {
        this.balance += value - this.tax;
    }

    @Override
    public void withdraw(Double value) {
        this.balance -= value + this.tax;
    }

    @Override
    public Double getBalance() {
        return this.balance;
    }
}
