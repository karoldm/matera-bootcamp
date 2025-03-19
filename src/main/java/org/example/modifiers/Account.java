package org.example.modifiers;

public class Account {
    private Double balance;

    public Account(Double balance) {
        this.balance = balance;
    }

    public Account() {}

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return this.balance;
    }
}
