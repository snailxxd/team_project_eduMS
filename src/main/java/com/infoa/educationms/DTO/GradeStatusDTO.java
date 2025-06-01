package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class GradeStatusDTO {
    private String id;
    private Integer studentId;
    private String studentName;
    private Integer courseId;
    private String courseName;
    private Integer grade;
    private String status;

}


