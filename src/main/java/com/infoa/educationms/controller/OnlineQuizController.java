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
public class OnlineQuizController {

    private final OnlineQuizService service;

    @Autowired
    public OnlineQuizController(OnlineQuizService service) {this.service = service;}

    @Autowired
    public GradeService gradeService;

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
    /*

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
    */
    // 设置考试成绩
    @PostMapping("/courses/section/{sectionId}/paper/{paperId}/students/exam")
    public ResponseEntity<Void> setExamScore(
            @PathVariable Integer sectionId,
            @PathVariable Integer paperId,
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
}