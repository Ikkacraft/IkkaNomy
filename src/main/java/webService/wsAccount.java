package webService;

public class wsAccount {
    private int account_id;
    private int account_balance;
    private String description;

    public wsAccount() {
    }

    public wsAccount(int account_id, int account_balance, String description) {
        this.account_id = account_id;
        this.account_balance = account_balance;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(int account_balance) {
        this.account_balance = account_balance;
    }
}
