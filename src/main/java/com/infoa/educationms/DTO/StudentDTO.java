package com.infoa.educationms.DTO;

import lombok.Data;
@Data
public class StudentDTO extends UserDTO {
    private String deptName;
    private Integer totCred;
}