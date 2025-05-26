package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "total_credit")
    private int totalCredit;

    // constructors
    public Student() {}

    public Student(int userId, String accountNumber, String password, int personalInfoId, String deptName, int totalCredit, UserRole userRole) {
        super(userId, accountNumber, password, personalInfoId, userRole);
        this.deptName = deptName;
        this.totalCredit = totalCredit;
    }

    // 转换为字符串
    @Override
    public String toString() {
        return "Student{" +
                "userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", personalInfoId=" + personalInfoId +
                ", deptName='" + deptName + '\'' +
                ", totalCredit=" + totalCredit +
                '}';
    }

    // getter and setter methods
    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}