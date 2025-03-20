package org.example.collections.sets;

import java.util.HashSet;
import java.util.Set;

public class SetsMain {
    public static void main(String[] args) {
        // set
        // the "hash" is an unique representation of the object at that moment, if two strings
        // has the same hash, for example, it won't be added
        Set<String> accountsSet = new HashSet<>();
        accountsSet.add("user one");
        // won't add because "user one" will generate always the same hash
        // so if the hash already exist, the set doesn't add
        accountsSet.add("user one");
        accountsSet.add(new String("user one"));
        // will add
        accountsSet.add("user two");
        // java is case-sensitive, will add -> the hash is different because in the
        // ASCII table the characters are different, so the generated hash will be different
        accountsSet.add("user Two");

        accountsSet.forEach(account -> {
            System.out.println("set: " + account);
            System.out.println("hash: " + account.hashCode());
        });

        // account tests
        Set<Account> accounts = new HashSet<>();
        accounts.add(new Account(1L, 100.0));
        // will be added (without the override of hashCode and equals)
        accounts.add(new Account(1L, 300.0));

        // won't add because the hash is generated based on the attributes,
        // the attributes are equals, so the hash are the same.
        // you can override the hashCode and equals methods on the Account class
        // to specific the way you want the generated the hash and how compare the objects.
        accounts.add(new Account(1L, 100.0));

        // builder example
        accounts.add(Account.builder().accountNumber(2L).balance(345.0).build());

        accounts.forEach(account -> {
            System.out.println("account: " + account);
        });

    }
}
