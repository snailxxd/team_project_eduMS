package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class CourseDTO {
    private Integer courseId;
    private String title;
    private String deptName;
    private Integer credits;
    private String courseIntroduction;
    private Integer capacity;
    private String requiredRoomType;
    private Integer gradeYear;
    private Integer period;
}