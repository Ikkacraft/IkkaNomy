package accounts;


public class Account {
    private int balance;
    private int accountID;
    private String description;

    public Account(int balance, int accountID, String description) {
        this.accountID = accountID;
        this.balance = balance;
        this.description = description;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void add(int value) {
        this.balance += value;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
