package com.infoa.educationms.service;


import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
import com.infoa.educationms.DTO.StudentGradeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    List<GradeDTO> getAllGrades(Integer studentId);

    List<StudentGradeDTO> getAllStudentGrades(Integer studentId);

    List<GradeStatusDTO> getAllStudentGradesBySection(Integer teacherId);
}