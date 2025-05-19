package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "take")
public class Take {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "take_id")
    private int takeId;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "section_id")
    private int sectionId;

    // constructors
    public Take() {};

    public Take(int takeId, int studentId, int sectionId) {
        this.takeId = takeId;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }

    // 转换为字符串
    public String toString() {
        return "Take{" +
                "takeId=" + takeId +
                ", studentId=" + studentId +
                ", sectionId=" + sectionId +
                '}';
    }

    // getter and setter methods
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
