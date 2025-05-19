package entities;

public class Admin extends User {
    @Override
    public UserRole getUserType() {
        return UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", personalInfoId=" + personalInfoId +
                '}';
    }
}

