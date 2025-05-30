package com.infoa.educationms.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @Column(name = "type")
    private String gradeType;

    @Column(name = "take_id")
    private int takeId;

    private String name;

    private int grade;

    private float proportion;

    // constructors
    public Grade() {};

    public Grade(int gradeId, String gradeType, int takeId, String name, int grade, float proportion) {
        this.gradeId = gradeId;
        this.gradeType = gradeType;
        this.takeId = takeId;
        this.name = name;
        this.grade = grade;
        this.proportion = proportion;
    }

    // 转换为字符串
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", name=" + name +
                ", gradeType='" + gradeType + '\'' +
                ", takeId=" + takeId +
                ", grade=" + grade +
                ", proportion=" + proportion +
                '}';
    }

    // getter and setter methods
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public int getTakeId() {
        return takeId;
    }

    public void setTakeId(int takeId) {
        this.takeId = takeId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

}
