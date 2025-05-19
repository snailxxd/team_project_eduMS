package entities;

public abstract class User {
    protected int userId;
    protected String accountNumber;
    protected String password;
    protected int personalInfoId;

    public User() {}

    public User(int userId, String accountNumber, String password, int personalInfoId) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.password = password;
        this.personalInfoId = personalInfoId;
    }

    public abstract UserRole getUserType();

    public abstract String toString();

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPersonalInfoId() { return personalInfoId; }

    public void setPersonalInfoId(int personalInfoId) { this.personalInfoId = personalInfoId; }

}
