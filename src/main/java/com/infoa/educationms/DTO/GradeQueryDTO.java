package com.infoa.educationms.DTO;


import java.util.List;
import lombok.Data;

@Data
public class GradeQueryDTO {
    private Integer courseId;
    private String courseName;
    private List<GradeTaskDTO> tasks;


}
