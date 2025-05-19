package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {

    @Column(name = "grade_type")
    private String gradeType;

    @Column(name = "take_id")
    private int takeId;

    private int grade;

    private float proportion;

    // constructors
    public Grade() {};

    public Grade(String gradeType, int takeId, int grade, float proportion) {
        this.gradeType = gradeType;
        this.takeId = takeId;
        this.grade = grade;
        this.proportion = proportion;
    }

    // 转换为字符串
    public String toString() {
        return "Grade{" +
                "gradeType=" + gradeType +
                ", takeId=" + takeId +
                ", grade=" + grade +
                ", proportion=" + proportion +
                '}';
    }

    // getter and setter methods
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
