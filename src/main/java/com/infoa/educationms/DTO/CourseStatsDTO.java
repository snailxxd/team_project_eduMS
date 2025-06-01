package com.infoa.educationms.DTO;

import java.util.List;
import lombok.Data;

@Data
public class CourseStatsDTO {
    private Integer teacherId;
    private Integer courseId;
    private String courseName;
    private Double average;
    private Double gpa;
    private  Integer totalStudents;
    private List<Integer> scores;

}
