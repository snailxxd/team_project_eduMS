package com.infoa.educationms.controller;

import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GradeController {

    @Autowired
    private EducationMSService educationMSService;

    @GetMapping("/grades/student/{studentId}")
    public ResponseEntity<ApiResult> getStudentGrades(@PathVariable Integer studentId) {
        // 服务方法未传 studentId，建议扩展接口
        ApiResult result = educationMSService.queryGrade();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grades/student/{studentId}/details")
    public ResponseEntity<ApiResult> getStudentGradeDetails(@PathVariable Integer studentId) {
        ApiResult result = educationMSService.analyzeGradeStu();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grades/teacher/{teacherId}")
    public ResponseEntity<ApiResult> getTeacherGrades(@PathVariable Integer teacherId) {
        ApiResult result = educationMSService.analyzeGradeTeacher(teacherId); // 复用成绩分析
        return ResponseEntity.ok(result);
    }
}
