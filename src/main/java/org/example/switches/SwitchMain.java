package org.example.switches;

public class SwitchMain {
    public static void main(String[] args) {
        SavingAccount savingAccount = new SavingAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        printAccountType(savingAccount);
        printAccountType(checkingAccount);
    }

    public static void printAccountType(Account account) {
        String message;
        switch (account.getAccountType()){
            case "Checking":
                message = "Checking account";
                break;
            case "Saving":
                message = "Saving account";
                break;
            default:
                message = "Invalid account type";
                break;
        }

        System.out.println(message);

        // in java 17 you can use
        String message2 = switch (account.getAccountType()) {
            case "Checking" -> "Checking account";
            case "Saving" -> "Saving account";
            default -> "Invalid account type";
        };

        System.out.println(message2);
    }
}
