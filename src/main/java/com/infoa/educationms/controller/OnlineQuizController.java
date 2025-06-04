package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.OqCourseForStudentDTO;
import com.infoa.educationms.DTO.OqCourseForTeacherDTO;
import com.infoa.educationms.DTO.OqStudentDTO;
import com.infoa.educationms.DTO.OqTimeSlotDTO;
import com.infoa.educationms.DTO.OqTeacherCourseInfoDTO;
import com.infoa.educationms.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infoa.educationms.service.OnlineQuizService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OnlineQuizController {

    private final OnlineQuizService service;

    @Autowired
    public OnlineQuizController(OnlineQuizService service) {this.service = service;}

    // 查询老师教授课程
    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<OqCourseForTeacherDTO>> getCoursesByTeacher(@PathVariable Integer teacherId) {
        List<OqCourseForTeacherDTO> courses = service.getCoursesByTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    // 查询课程所有学生
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<OqStudentDTO>> getStudentsByCourse(@PathVariable Integer courseId) {
        List<OqStudentDTO> students = service.getStudentsByCourse(courseId);
        return ResponseEntity.ok(students);
    }

    // 查询学生所有参加的课程
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<OqCourseForStudentDTO>> getCoursesByStudent(@PathVariable Integer studentId) {
        List<OqCourseForStudentDTO> courses = service.getCoursesByStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    // 设置考试成绩占比
    @PostMapping("/courses/{courseId}/exam/proportion")
    public ResponseEntity<Void> setExamProportion(
            @PathVariable Integer courseId,
            @RequestBody Map<String, Double> requestBody) {
        Double proportion = requestBody.get("proportion");
        if (proportion == null || proportion < 0 || proportion > 1) {
            return ResponseEntity.badRequest().build();
        }
        service.setExamProportion(courseId, proportion);
        return ResponseEntity.ok().build();
    }

    // 设置考试成绩
    @PostMapping("/courses/{courseId}/students/{studentId}/exam")
    public ResponseEntity<Void> setExamScore(
            @PathVariable Integer courseId,
            @PathVariable Integer studentId,
            @RequestBody Map<String, Integer> requestBody) {
        Integer score = requestBody.get("score");
        if (score == null || score < 0 || score > 100) {
            return ResponseEntity.badRequest().build();
        }
        service.setExamScore(courseId, studentId, score);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/courses/{courseId}/sections")
    public ResponseEntity<List<Integer>> getSectionIdsByCourse(@PathVariable Integer courseId) {
        List<Integer> sectionIds = service.getSectionIdsByCourse(courseId);
        return ResponseEntity.ok(sectionIds);
    }

    @GetMapping("/sections/{sectionId}/time-slots")
    public ResponseEntity<List<OqTimeSlotDTO>> getTimeSlotInfoBySection(@PathVariable Integer sectionId) {
        List<OqTimeSlotDTO> timeSlots = service.getTimeSlotInfoBySection(sectionId);
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/teachers/{teacherId}/course-details")
    public ResponseEntity<List<OqTeacherCourseInfoDTO>> getTeacherCourseDetails(@PathVariable Integer teacherId) {
        List<OqTeacherCourseInfoDTO> courses = service.getTeacherCourseDetails(teacherId);
        return ResponseEntity.ok(courses);
    }
}