package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.DepartmentDTO;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.DepartmentService;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> result = departmentService.queryAllDepartments();
        return ResponseEntity.ok(result);
    }
}