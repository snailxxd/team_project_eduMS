package entities;

import java.time.LocalDateTime;

public class GradeChange {
    private int takeId;
    private int teacherId;
    private boolean result;
    private int newGrade;
    private LocalDateTime applyTime;
    private LocalDateTime checkTime;

    public GradeChange() {};

    public GradeChange(int takeId, int teacherId, boolean result, int newGrade, LocalDateTime applyTime, LocalDateTime checkTime) {
        this.takeId = takeId;
        this.teacherId = teacherId;
        this.result = result;
        this.newGrade = newGrade;
        this.applyTime = applyTime;
        this.checkTime = checkTime;
    }

    public String toString() {
        return "GradeChange{" +
                "takeId=" + takeId +
                ", teacherId=" + teacherId +
                ", result=" + result +
                ", newGrade=" + newGrade +
                ", applyTime=" + applyTime +
                ", checkTime=" + checkTime +
                '}';
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
