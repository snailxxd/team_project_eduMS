package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class SendGradeDTO {
    int studentId;
    int score;
    String name;
    float proportion;
}