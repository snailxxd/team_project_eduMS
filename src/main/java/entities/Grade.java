package entities;

public class Grade {
    private GradeType gradeType;
    private int takeId;
    private int grade;
    private float proportion;

    public Grade() {};

    public Grade(GradeType gradeType, int takeId, int grade, float proportion) {
        this.gradeType = gradeType;
        this.takeId = takeId;
        this.grade = grade;
        this.proportion = proportion;
    }

    public String toString() {
        return "Grade{" +
                "gradeType=" + gradeType +
                ", takeId=" + takeId +
                ", grade=" + grade +
                ", proportion=" + proportion +
                '}';
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
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
