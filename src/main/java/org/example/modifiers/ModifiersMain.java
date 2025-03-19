package org.example.modifiers;

public class ModifiersMain {
    public static void main(String[] args) {
        Account account = new Account();

        account.setBalance(10.0);
        System.out.println(account.getBalance());


        // test finals

        final Account accountFinal = new Account(10.0);
        accountFinal.setBalance(1000.0);

        System.out.println(accountFinal.getBalance());

        // error: Cannot assign a value to final variable 'accountFinal'
        // accountFinal = new Account(50.0);
    }
}
