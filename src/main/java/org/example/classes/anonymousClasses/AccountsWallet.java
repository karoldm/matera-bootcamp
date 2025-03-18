package org.example.classes.anonymousClasses;

public class AccountsWallet {
    Account checkingAccount = new Account(){
        @Override
        public void printExtract() {
            System.out.println("Checkin Account extract");
        }
    };

    Account savingAccount = new Account(){
        @Override
        public void printExtract() {
            System.out.println("Saving Account extract");
        }
    };
}
