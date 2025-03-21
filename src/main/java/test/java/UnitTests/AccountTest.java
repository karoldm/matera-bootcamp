package test.java.UnitTests;

import org.example.tests.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    Double balance = 1000.0;
    Double tax = 0.5;
    Double finalBalance = 799.5;

    @Test
    public void depositTest() {
        Account account = new Account(balance, tax);
        account.withdraw(200.0);
        assertEquals(finalBalance, account.getBalance());
    }
}
