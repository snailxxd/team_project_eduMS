package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
import com.infoa.educationms.DTO.OutGradeDTO;
import com.infoa.educationms.DTO.StudentGradeDTO;
import com.infoa.educationms.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    //成绩查询
    @GetMapping("/grades/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getStudentGrades(@PathVariable Integer studentId) {
        List<GradeDTO> result = gradeService.getAllGrades(studentId);
        return ResponseEntity.ok(result);
    }
    //成绩分析
    @GetMapping("/grades/student/{studentId}/details")
    public ResponseEntity<List<StudentGradeDTO>> getStudentGradeDetails(@PathVariable Integer studentId) {
        List<StudentGradeDTO> result = gradeService.getAllStudentGrades(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/grades/teacher/{teacherId}")
    public ResponseEntity<List<GradeStatusDTO>> getTeacherGrades(@PathVariable Integer teacherId) {
        List<GradeStatusDTO> result = gradeService.getAllStudentGradesBySection(teacherId);
        return ResponseEntity.ok(result);
    }



}
