package org.example.classes.anonymousClasses;

public class AnonymousClassesMain {
    public static void main(String[] args) {
        AccountsWallet accountsWallet = new AccountsWallet();
        accountsWallet.checkingAccount.printExtract();
        accountsWallet.savingAccount.printExtract();
    }
}
