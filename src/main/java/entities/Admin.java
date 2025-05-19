package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    @Override
    public UserRole getUserType() {
        return UserRole.ADMIN;
    }

    // 转换为字符串
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

