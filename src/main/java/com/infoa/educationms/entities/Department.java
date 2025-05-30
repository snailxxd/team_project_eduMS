package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_name")
    private String deptName;

    private String campus;

    // constructors
    public Department() {}

    public Department(String deptName, String campus) {
        this.deptName = deptName;
        this.campus = campus;
    }

    // 转换为字符串
    public String toString() {
        return "Department{" +
                "deptName='" + deptName + '\'' +
                ", campus='" + campus + '\'' +
                '}';
    }

    // getter and setter methods
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}
