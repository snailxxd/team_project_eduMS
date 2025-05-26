package com.infoa.educationms.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grade_change")
public class GradeChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_change_id")
    private int changeId;

    @Column(name = "take_id")
    private int takeId;

    @Column(name = "teacher_id")
    private int teacherId;

    private boolean result;

    @Column(name = "new_grade")
    private int newGrade;

    @Column(name = "apply_time")
    private LocalDateTime applyTime;

    @Column(name = "check_time")
    private LocalDateTime checkTime;

    // constructors
    public GradeChange() {};

    public GradeChange(int changeId, int takeId, int teacherId, boolean result, int newGrade, LocalDateTime applyTime, LocalDateTime checkTime) {
        this.changeId = changeId;
        this.takeId = takeId;
        this.teacherId = teacherId;
        this.result = result;
        this.newGrade = newGrade;
        this.applyTime = applyTime;
        this.checkTime = checkTime;
    }

    // 转换为字符串
    public String toString() {
        return "GradeChange{" +
                "changeId=" + changeId +
                ", takeId=" + takeId +
                ", teacherId=" + teacherId +
                ", result=" + result +
                ", newGrade=" + newGrade +
                ", applyTime=" + applyTime +
                ", checkTime=" + checkTime +
                '}';
    }

    // getter and setter methods
    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public int getTakeId() {
        return takeId;
    }

    public void setTakeId(int takeId) {
        this.takeId = takeId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(int newGrade) {
        this.newGrade = newGrade;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }
}
