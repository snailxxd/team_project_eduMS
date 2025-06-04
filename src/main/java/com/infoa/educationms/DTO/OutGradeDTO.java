package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class OutGradeDTO {
    private Integer grade;
    private Float proportion;
    private String type; // 'attending', 'homework', 'test'
    private Integer studentId;
    private Integer secId;
    private String Name;
    private int id;
}