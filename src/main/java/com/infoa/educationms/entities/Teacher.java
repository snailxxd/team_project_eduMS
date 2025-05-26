package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    @Column(name = "dept_name")
    private String deptName;

    private int salary;

    // constructors
    public Teacher() {};

    public Teacher(int userId, String accountNumber, String password, int personalInfoId, String deptName, int salary) {
        super(userId, accountNumber, password, personalInfoId);
        this.deptName = deptName;
        this.salary = salary;
    }

    @Override
    public UserRole getUserType() {
        return UserRole.teacher;
    }

    // 转换为字符串
    @Override
    public String toString() {
        return "Teacher{" +
                "userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", personalInfoId=" + personalInfoId +
                ", deptName='" + deptName + '\'' +
                ", salary=" + salary +
                '}';
    }

    // getter and setter methods
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}