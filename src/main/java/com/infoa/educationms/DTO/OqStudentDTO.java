package com.infoa.educationms.DTO;

public class OqStudentDTO {
    private Integer studentId;
    private String studentName;
    private String department;

    // Constructors, getters, and setters
    public OqStudentDTO() {}

    public OqStudentDTO(Integer studentId, String studentName, String department) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.department = department;
    }

    // Getters and setters
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
