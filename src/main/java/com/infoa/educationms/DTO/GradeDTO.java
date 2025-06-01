package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class GradeDTO {
    private Integer gradeId;
    private Integer takesId;
    private Integer grade;
    private Double proportion;
    private String type; // 'attending', 'homework', 'test'
    private Integer studentId;
    private String studentName;
    private Integer courseId;
    private String courseName;

}
