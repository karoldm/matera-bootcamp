package org.example.overrideAndOverload;


public class overrideAndOverloadMain {
    public static void main(String[] args) {
        // override is used when we want to replace the behavior of a method

        Account commonAccount = new Account(100.0);
        commonAccount.deposit(10.0);
        System.out.println(commonAccount.getBalance());

        SavingAccount savingAccount = new SavingAccount(100.0);
        savingAccount.deposit(10.0);
        System.out.println(savingAccount.getBalance());

        // overload happens when you have multiples methods with the same name but with different args

        SavingAccount savingAccount2 = new SavingAccount(100.0);

        savingAccount2.withdraw(10.0);
        System.out.println(savingAccount2.getBalance());

        savingAccount2.deposit(10.0);
        savingAccount2.withdraw(10.0, 1.0);
        System.out.println(savingAccount2.getBalance());
    }
}
