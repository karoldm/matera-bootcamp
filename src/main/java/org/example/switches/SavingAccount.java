package org.example.switches;

public class SavingAccount implements Account {
    private String type;

    public SavingAccount() {
        this.type = "Saving";
    }

    @Override
    public String getAccountType() {
        return this.type;
    }
}
