package com.infoa.educationms.service;

import com.infoa.educationms.DTO.DepartmentDTO;
import com.infoa.educationms.entities.Department;
import com.infoa.educationms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> queryAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 私有方法：将 Department 转换为 DepartmentDTO
    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDeptName(department.getDeptName());
        dto.setCampus(department.getCampus());
        return dto;
    }
}
