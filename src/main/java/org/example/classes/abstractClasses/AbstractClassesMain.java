package org.example.classes.abstractClasses;

public class AbstractClassesMain {
    public static void main(String[] args) {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.printExtract();

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.printExtract();

    }
}
