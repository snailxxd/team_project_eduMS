package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class StudentRankDTO {
    private Integer rank;
    private Integer studentId;
    private String studentName;
    private Integer score;
    private Float gpa;


}
