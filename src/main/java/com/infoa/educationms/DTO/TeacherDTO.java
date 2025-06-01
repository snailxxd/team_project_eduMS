package com.infoa.educationms.DTO;

import lombok.Data;
@Data
public class TeacherDTO extends UserDTO {
    private String deptName;
    private Integer salary;
}