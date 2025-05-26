package com.infoa.educationms.controller;

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
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = service.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // 查询所有教室信息
    @GetMapping("/classrooms")
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        List<Classroom> classrooms = service.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    // 查询所有老师信息
    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = service.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    // 添加教室
    @PostMapping("/classrooms")
    public ResponseEntity<Void> addClassroom(@RequestBody Classroom classroom) {
        service.addClassroom(classroom);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 修改教室信息
    @PutMapping("/classrooms/{classroomId}")
    public ResponseEntity<Void> updateClassroom(
            @PathVariable Integer classroomId,
            @RequestBody Classroom classroom) {
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