package com.infoa.educationms.DTO;

public class CourseDTO {
    private int courseId;
    private String courseName;
    private int credit;
    private String teacher;
    private String classroom;

    // Constructors, getters, and setters
    public CourseDTO() {}

    public CourseDTO(int courseId, String courseName, int credit, String teacher, String classroom) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    // Getters and setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
}