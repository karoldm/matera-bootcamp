package org.example.abstractClasses;

public abstract class Account {
    private Double  balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    abstract public void printExtract();
}
