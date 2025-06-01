package com.infoa.educationms.controller;


import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;
import com.infoa.educationms.entities.Section;
import com.infoa.educationms.repository.SectionRepository;
import com.infoa.educationms.service.CourseArrangementService;
import com.infoa.educationms.service.CourseResourceService;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private ;

    @Autowired
    private SectionRepository sectionRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> result = .();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO dto) {
        Section section = convertToSection(dto);
        CourseDTO result = educationMSService.addSection(section);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseDTO dto) {
        Section section = convertToSection(dto);
        section.setSectionId(courseId); // 设置ID
        CourseDTO result = educationMSService.updateSection(section);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        educationMSService.deleteSection(courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/courses/stats")
    public ResponseEntity<CourseStatsDTO> getCourseStats() {
        // 默认用 sectionId = -1，服务层可以返回全部或需扩展接口
        CourseStatsDTO result = educationMSService.analyzeGradeTeacher(-1);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/courses/{courseId}/student-ranks")
    public ResponseEntity<List<StudentRankDTO>> getCourseStudentRanks(@PathVariable Integer courseId) {
        List<StudentRankDTO> result = educationMSService.analyzeGradeTeacher(courseId);
        return ResponseEntity.ok(result);
    }

    private Section convertToSection(CourseDTO dto) {
        return sectionRepository.findFirstByCourseId(dto.getCourseId()).get();
    }

}