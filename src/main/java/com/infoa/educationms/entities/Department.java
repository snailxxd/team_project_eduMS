package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_name")
    private String deptName;

    private String building;

    // constructors
    public Department() {}

    public Department(String deptName, String building) {
        this.deptName = deptName;
        this.building = building;
    }

    // 转换为字符串
    public String toString() {
        return "Department{" +
                "deptName='" + deptName + '\'' +
                ", building='" + building + '\'' +
                '}';
    }

    // getter and setter methods
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
