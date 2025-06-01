package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.ClassroomDTO;
import com.infoa.educationms.DTO.ClassroomUpdateDTO;
import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.TeacherDTO;
import com.infoa.educationms.entities.Classroom;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infoa.educationms.service.CourseArrangementService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CourseArrangementController {

    private final CourseArrangementService service;

    @Autowired
    public CourseArrangementController(CourseArrangementService service) {
        this.service = service;
    }

    // 查询所有课程信息
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(service.getAllCourses());
    }

    // 查询所有教室信息
    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        List<ClassroomDTO> classrooms = service.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    // 查询所有老师信息
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = service.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    // 添加教室
    @PostMapping("/classrooms")
    public ResponseEntity<Void> addClassroom(@RequestBody ClassroomDTO classroom) {
        service.addClassroom(classroom);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 修改教室信息
    @PutMapping("/classrooms/{classroomId}")
    public ResponseEntity<Void> updateClassroom(
            @PathVariable Integer classroomId,
            @RequestBody ClassroomUpdateDTO classroom) {
        service.updateClassroom(classroomId, classroom);
        return ResponseEntity.ok().build();
    }

    // 删除教室
    @DeleteMapping("/classrooms/{classroomId}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Integer classroomId) {
        service.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }
}