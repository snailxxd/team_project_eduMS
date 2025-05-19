package entities;

public class Take {
    private int takeId;
    private int studentId;
    private int sectionId;

    public Take() {};

    public Take(int takeId, int studentId, int sectionId) {
        this.takeId = takeId;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    public String toString() {
        return "Take{" +
                "takeId=" + takeId +
                ", studentId=" + studentId +
                ", sectionId=" + sectionId +
                '}';
    }

    public int getTakeId() {
        return takeId;
    }

    public void setTakeId(int takeId) {
        this.takeId = takeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
