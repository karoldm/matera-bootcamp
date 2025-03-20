package org.example.optional;

import org.example.collections.sets.Account;

import java.util.Optional;

public class optionalMain {
    public static void main(String[] args) {
        Account account = getAccount();

        // NullPointerException
        // System.out.println(account.getBalance());

        // but in java 8 we have the Optional :)
        Optional<Account> accountOption = getAccountOptional();
        accountOption.ifPresent(value -> {
            System.out.println(value.getBalance());
        });
        // or
        if(accountOption.isPresent()){
            System.out.println(accountOption.get().getBalance());
        } else {
            System.out.println("Account is empty");
        }
    }

    public static Account getAccount() {
        return null;
    }

    public static Optional<Account> getAccountOptional(){
        return Optional.empty();
        //if you return null or Optional.of(null) the exception will be the same of the getAccount!
    }
}
