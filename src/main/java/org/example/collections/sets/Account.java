package org.example.collections.sets;

import lombok.Builder;
import lombok.Data;

// allow you to create an Account object with named parameters using the builder pattern
@Builder
// add a lot of annotations at the same time (get, set, constructor, toString, equals)
@Data
public class Account {
    private Long accountNumber;
    private Double balance;

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

    // it prevents that accounts with the same accountNumber, but different balance
    // be added on the set
    @Override
    public boolean equals(Object obj) {
        Account acc = (Account) obj;
        return this.accountNumber.equals(acc.getAccountNumber());
    }
}
