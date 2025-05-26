package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int userId;

    @Column(name = "account_number")
    protected String accountNumber;

    protected String password;

    @Column(name = "personal_infor_id")
    protected int personalInfoId;

    @Column(name = "type")
    protected UserRole userType;

    // constructors
    public User() {}

    public User(int userId, String accountNumber, String password, int personalInfoId) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.password = password;
        this.personalInfoId = personalInfoId;
    }

    // getter and setter methods
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
