package com.infoa.educationms.service;


import com.infoa.educationms.DTO.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> queryAllDepartments();
}
