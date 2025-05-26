package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_infomation")
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_infor_id")
    private int personalInfoId;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String picture;

    // constructors
    public PersonalInfo() {}

    public PersonalInfo(int personalInfoId, String name, String phoneNumber, String picture) {
        this.personalInfoId = personalInfoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

    // 转换为字符串
    public String toString() {
        return "PersonalInfo{" +
                "personalInfoId=" + personalInfoId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", picture='" + picture + '\'' +
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
