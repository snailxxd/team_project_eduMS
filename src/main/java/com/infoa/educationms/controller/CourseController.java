package com.infoa.educationms.controller;


import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.entities.Section;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private EducationMSService educationMSService;

    @GetMapping("/courses")
    public ResponseEntity<ApiResult> getAllCourses() {
        ApiResult result = educationMSService.querySection();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/courses")
    public ResponseEntity<ApiResult> createCourse(@RequestBody CourseDTO dto) {
        Section section = convertToSection(dto);
        ApiResult result = educationMSService.addSection(section);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<ApiResult> updateCourse(@PathVariable int courseId, @RequestBody CourseDTO dto) {
        Section section = convertToSection(dto);
        section.setSectionId(courseId); // 设置ID
        ApiResult result = educationMSService.updateSection(section);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ApiResult> deleteCourse(@PathVariable int courseId) {
        ApiResult result = educationMSService.deleteSection(courseId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/courses/stats")
    public ResponseEntity<ApiResult> getCourseStats() {
        // 默认用 sectionId = -1，服务层可以返回全部或需扩展接口
        ApiResult result = educationMSService.analyzeGradeTeacher(-1);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/courses/{courseId}/student-ranks")
    public ResponseEntity<ApiResult> getCourseStudentRanks(@PathVariable Integer courseId) {
        ApiResult result = educationMSService.analyzeGradeTeacher(courseId);
        return ResponseEntity.ok(result);
    }

    private Section convertToSection(CourseDTO dto) {
        Section section = new Section();
        section.setCourseId(dto.getCourseId());
        section.setSemester(dto.getSemester());
        section.setYear(dto.getYear());
        section.setClassroomId(dto.getClassroomId());
        section.setTimeSlotIdList(dto.getTimeSlotIds());
        section.setTeacherId(dto.getTeacherId());
        section.setTime(dto.getTime());
        return section;
    }

}