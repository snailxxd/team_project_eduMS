package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "administrator")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

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

