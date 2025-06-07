package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infoa.educationms.service.OnlineQuizService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")

public class OnlineQuizController {

    private final OnlineQuizService service;

    @Autowired
    public OnlineQuizController(OnlineQuizService service) {this.service = service;}

    @Autowired
    public GradeService gradeService;
    // 查询老师教授课程
    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<OqCourseForTeacherDTO>> getCoursesByTeacher(@PathVariable("teacherId") Integer teacherId) {
        List<OqCourseForTeacherDTO> courses = service.getCoursesByTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    // 查询课程所有学生
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<OqStudentDTO>> getStudentsByCourse(@PathVariable("courseId") Integer courseId) {
        List<OqStudentDTO> students = service.getStudentsByCourse(courseId);
        return ResponseEntity.ok(students);
    }

    // 查询学生所有参加的课程
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<OqCourseForStudentDTO>> getCoursesByStudent(@PathVariable("studentId") Integer studentId) {
        List<OqCourseForStudentDTO> courses = service.getCoursesByStudent(studentId);
        System.out.println("courses");
        System.out.println(courses);
        return ResponseEntity.ok(courses);
    }

    // 设置考试成绩
    @PostMapping("/courses/section/{sectionId}/paper/{paperId}/students/exam")
    public ResponseEntity<Void> setExamScore(
            @PathVariable("sectionId") Integer sectionId,
            @PathVariable("paperId") Integer paperId,
            @RequestBody List<SendGradeDTO> requestBody) {
        List<OutGradeDTO> outGradeDTOs = new ArrayList<>();
        for (SendGradeDTO gradeDTO : requestBody) {
            OutGradeDTO outGradeDTO = new OutGradeDTO();
            outGradeDTO.setType("test");
            outGradeDTO.setSecId(sectionId);
            outGradeDTO.setId(paperId);
            outGradeDTO.setStudentId(gradeDTO.getStudentId());
            outGradeDTO.setGrade(gradeDTO.getScore());
            outGradeDTO.setName(gradeDTO.getName());
            outGradeDTO.setProportion(gradeDTO.getProportion());
            outGradeDTOs.add(outGradeDTO);
        }
        gradeService.addGrade(outGradeDTOs);
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
    public ResponseEntity<List<OqTeacherCourseInfoDTO>> getTeacherCourseDetails(@PathVariable("teacherId") Integer teacherId) {
        List<OqTeacherCourseInfoDTO> courses = service.getTeacherCourseDetails(teacherId);
        return ResponseEntity.ok(courses);
    }
}