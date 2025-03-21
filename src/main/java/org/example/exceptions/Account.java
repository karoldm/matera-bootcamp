package org.example.exceptions;

import lombok.Data;

@Data
public class Account {
    private String type;
    private Double balance;
    private Double tax;

    public void withdraw(Double amount) throws IllegalAmountException, InsufficientBalanceException {
        double valueToDiscount = amount + this.tax;

        if(amount <= 0.0) {
            throw new IllegalAmountException(amount);
        }

        if(this.balance <= 0.0 || this.balance < valueToDiscount) {
            throw new InsufficientBalanceException(balance, valueToDiscount);
        }

        this.balance -= valueToDiscount;
    }

}
