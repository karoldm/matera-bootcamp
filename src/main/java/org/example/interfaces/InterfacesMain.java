package org.example.interfaces;

public class InterfacesMain {
    public static void main(String[] args) {
        CheckingAccount  checkingAccount = new CheckingAccount();
        checkingAccount.deposit(10.0);
        System.out.println("Account balance is " + checkingAccount.getBalance());

        checkingAccount.withdraw(9.4);
        System.out.println("Account balance is " + checkingAccount.getBalance());

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.deposit(10.0);
        System.out.println("Account balance is " + savingAccount.getBalance());

        savingAccount.withdraw(10.0);
        System.out.println("Account balance is " + savingAccount.getBalance());
    }
}
