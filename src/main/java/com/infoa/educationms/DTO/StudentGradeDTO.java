package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class StudentGradeDTO {
    private Integer courseId;
    private String courseName;
    private Integer score;
    private Integer credits;
    private Double gpa;
    private String semester;

}
