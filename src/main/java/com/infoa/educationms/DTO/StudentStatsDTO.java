package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class StudentStatsDTO {
    private Integer totalCredits;
    private Double averageScore;
    private Double gpa;
}
