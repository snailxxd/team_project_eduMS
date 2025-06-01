package com.infoa.educationms.DTO;

public class OqCourseForTeacherDTO {
    private Integer courseId;
    private String courseName;
    private int credit;

    // Constructors, getters, and setters
    public OqCourseForTeacherDTO() {}

    public OqCourseForTeacherDTO(Integer courseId, String courseName, int credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
    }

    // Getters and setters
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
}