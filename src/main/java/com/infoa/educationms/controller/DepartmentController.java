package com.infoa.educationms.controller;

import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private EducationMSService educationMSService;

    @GetMapping("/departments")
    public ResponseEntity<ApiResult> getAllDepartments() {
        ApiResult result = educationMSService.queryDepartments(); // 你需要先在接口里定义这个方法
        return ResponseEntity.ok(result);
    }
}