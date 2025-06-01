package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.*;

import com.infoa.educationms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // 获取当前登录用户信息
    @GetMapping("/user/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        return ResponseEntity.ok(userDTO);
    }

    // 获取所有用户（此处调用 searchUser）
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = userService.searchUser("", null);
        return ResponseEntity.ok(result);
    }

    // 创建用户（仅限通用用户）
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO result = userService.addUser(userDTO);
        return ResponseEntity.ok(result);
    }

    // 获取所有学生
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> result = userService.getAllStudents();
        return ResponseEntity.ok(result);
    }

    // 获取某个学生信息
    @GetMapping("/students/{userId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int userId) {
        StudentDTO result = userService.getStudentById(userId);
        return ResponseEntity.ok(result);
    }

    // 创建学生
    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO result = userService.createStudent(studentDTO);
        return ResponseEntity.ok(result);
    }

    // 更新学生
    @PutMapping("/students/{userId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable int userId, @RequestBody StudentDTO studentDTO) {
        StudentDTO result = userService.updateStudent(userId, studentDTO);
        return ResponseEntity.ok(result);
    }

    // 获取教师信息
    @GetMapping("/teachers/{userId}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable int userId) {
        TeacherDTO result = userService.getTeacherById(userId);
        return ResponseEntity.ok(result);
    }

    // 创建教师
    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO result = userService.createTeacher(teacherDTO);
        return ResponseEntity.ok(result);
    }

    // 更新教师
    @PutMapping("/teachers/{userId}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable int userId, @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO result = userService.updateTeacher(userId, teacherDTO);
        return ResponseEntity.ok(result);
    }

    // 创建管理员
    @PostMapping("/administrators")
    public ResponseEntity<AdministratorDTO> createAdmin(@RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO result = userService.createAdmin(adminDTO);
        return ResponseEntity.ok(result);
    }
}
