package entities;

public class Section {
    private int sectionId;
    private int courseId;
    private String semester;
    private int year;
    private int classroomId;
    private int timeSlotId;
    private int teacherId;
    private int time;

    public Section() {};

    public Section(int sectionId, int courseId, String semester, int year, int classroomId, int timeSlotId, int teacherId) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.semester = semester;
        this.year = year;
        this.classroomId = classroomId;
        this.timeSlotId = timeSlotId;
        this.teacherId = teacherId;
    }

    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", courseId=" + courseId +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", classroomId=" + classroomId +
                ", timeSlotId=" + timeSlotId +
                ", teacherId=" + teacherId +
                '}';
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}