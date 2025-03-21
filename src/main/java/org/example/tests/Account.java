package org.example.tests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private Double balance;
    private Double tax;

    public void deposit(Double amount) {
        balance += amount - this.tax;
    }

    public void withdraw(Double amount) {
        balance -= amount + this.tax;
        // to simulate a fail
        // balance -= amount - this.tax;
    }
}
