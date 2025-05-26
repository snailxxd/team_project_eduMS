package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_info")
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_info_id")
    private int personalInfoId;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    // constructors
    public PersonalInfo() {}

    public PersonalInfo(int personalInfoId, String name, String phoneNumber) {
        this.personalInfoId = personalInfoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // 转换为字符串
    public String toString() {
        return "PersonalInfo{" +
                "personalInfoId=" + personalInfoId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // getter and setter methods
    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
