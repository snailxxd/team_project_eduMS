package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_information")
public class PersonalInfor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_infor_id")
    private int personalInforId;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String picture;

    // constructors
    public PersonalInfor() {}

    public PersonalInfor(int userid, String name, String phoneNumber, String picture) {
        this.personalInforId = userid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

    // 转换为字符串
    public String toString() {
        return "PersonalInfo{" +
                "personalInfoId=" + personalInforId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", picture='" + picture + '\'' +
                '}';
   }

    // getter and setter methods
    public int getPersonalInfoId() {
        return personalInforId;
    }

    public void setPersonalInfoId(int personalInforId) {
        this.personalInforId = personalInforId;
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
