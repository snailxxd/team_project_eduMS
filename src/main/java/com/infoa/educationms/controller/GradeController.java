package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
import com.infoa.educationms.DTO.StudentGradeDTO;
import com.infoa.educationms.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/grades/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getStudentGrades(@PathVariable Integer studentId) {
        // 服务方法未传 studentId，建议扩展接口
        List<GradeDTO> result = gradeService.getAllGrades(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grades/student/{studentId}/details")
    public ResponseEntity<List<StudentGradeDTO>> getStudentGradeDetails(@PathVariable Integer studentId) {
        List<StudentGradeDTO> result = gradeService.getAllStudentGrades(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grades/teacher/{teacherId}")
    public ResponseEntity<List<GradeStatusDTO>> getTeacherGrades(@PathVariable Integer teacherId) {
        // 复用成绩分析

        List<GradeStatusDTO> result = gradeService.getAllStudentGradesBySection(teacherId);
        return ResponseEntity.ok(result);
    }
}
