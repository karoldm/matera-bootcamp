package org.example.switches;

public class CheckingAccount implements Account {
    private String type;

    public CheckingAccount() {
        this.type = "Checking";
    }

    @Override
    public String getAccountType() {
        return this.type;
    }
}
