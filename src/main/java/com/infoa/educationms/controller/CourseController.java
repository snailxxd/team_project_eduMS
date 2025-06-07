package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;
import com.infoa.educationms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> result = courseService.getAllCourses();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO dto) {
        CourseDTO result = courseService.addCourse(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseDTO dto) {
        dto.setCourseId(courseId);  // 确保ID一致
        CourseDTO result = courseService.updateCourse(courseId, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats/{teacherId}")
    public ResponseEntity<List<CourseStatsDTO>> getCourseStats(@PathVariable Integer teacherId) {
        List<CourseStatsDTO> result = courseService.getCourseStats(teacherId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{courseId}/student-ranks")
    public ResponseEntity<List<StudentRankDTO>> getCourseStudentRanks(@PathVariable int courseId) {
        List<StudentRankDTO> result = courseService.getCourseStudentRanks(courseId);
        return ResponseEntity.ok(result);
    }
}
