package org.example.exceptions;

public class ExceptionsMain {
    public static void main(String[] args) {
        Account account = new Account();
        // throw InsufficientBalanceException
        account.setBalance(100.0);
        account.setTax(0.5);

        // doesn't throw a Exception
        // account.setTax(0.0);

        try {
            System.out.println("Initial balance: " + account.getBalance());
            account.withdraw(100.0);
            // throw IllegalAmountException
            // account.withdraw(-100.0);
            System.out.println("Success");
        } catch (IllegalAmountException | InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } catch(NullPointerException e) {
            // will throw when you doesn't set the tax/balance
            System.out.println("Oh! You probably forgot to set the tax or the initial balance.");
        } finally {
            System.out.println("Final balance: " + account.getBalance());
        }
    }
}
