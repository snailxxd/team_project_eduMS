package com.infoa.educationms.DTO;

import java.util.List;
import lombok.Data;

@Data
public class CourseStatsDTO {
    private Integer teacherId;
    private Integer courseId;
    private String courseName;
    private Float average;
    private Float gpa;
    private  Integer totalStudents;
    private List<Integer> scores;

}
