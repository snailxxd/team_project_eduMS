package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "classroom")
public class Classroom {

    private String campus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private int classroomId;

    @Column(name = "room_number")
    private int roomNumber;

    private int capacity;

    private String building;

    private String type;

    // constructors
    public Classroom() {};

    public Classroom(String campus, int classroomId, int roomNumber, int capacity, String building) {
        this.campus = campus;
        this.classroomId = classroomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.building = building;
    }

    // 转换为字符串
    public String toString() {
        return "Classroom{" +
                "campus='" + campus + '\'' +
                ", classroomId=" + classroomId +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", building='" + building + '\'' +
                '}';
    }

    // getter and setter methods
    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}