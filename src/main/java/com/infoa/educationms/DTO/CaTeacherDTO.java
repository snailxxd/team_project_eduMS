package com.infoa.educationms.DTO;

public class CaTeacherDTO {
    private Integer teacherId;
    private String teacherName;
    private String department;

    // Constructors, getters, and setters
    public CaTeacherDTO() {}

    public CaTeacherDTO(Integer teacherId, String teacherName, String department) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.department = department;
    }

    // Getters and setters
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}