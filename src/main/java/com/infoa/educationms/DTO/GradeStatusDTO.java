package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class GradeStatusDTO {
    private String id;
    private Integer studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private String grade;
    private String status;
    private Boolean hasApplicationListener;

}


