package com.infoa.educationms.DTO;

public class OqCourseForStudentDTO {
    private Integer courseId;
    private Integer sectionId;
    private String courseName;
    private int credit;
    private String teacher;

    // Constructors, getters, and setters
    public OqCourseForStudentDTO() {}

    public OqCourseForStudentDTO(Integer courseId, Integer sectionId, String courseName, int credit, String teacher) {
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.courseName = courseName;
        this.credit = credit;
        this.teacher = teacher;
    }

    // Getters and setters
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public Integer getSectionId() { return sectionId;}
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId;}
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public String getTeacher() { return teacher;}
    public void setTeacher(String teacher) { this.teacher = teacher; }
}
