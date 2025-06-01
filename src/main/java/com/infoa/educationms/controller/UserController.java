package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.User;
import com.infoa.educationms.entities.UserRole;
import com.infoa.educationms.entities.PersonalInfo;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private EducationMSService educationMSService;

    // 获取当前登录用户信息
    @GetMapping("/user/current")
    public ResponseEntity<ApiResult> getCurrentUser(@RequestParam int userId) {
        ApiResult result = educationMSService.getPersonalInfo(userId);
        return ResponseEntity.ok(result);
    }

    // 获取所有用户（此处调用 searchUser）
    @GetMapping("/users")
    public ResponseEntity<ApiResult> getAllUsers() {
        ApiResult result = educationMSService.searchUser("", null);
        return ResponseEntity.ok(result);
    }

    // 创建用户
    @PostMapping("/users")
    public ResponseEntity<ApiResult> createUser(@RequestBody UserDTO userDTO) {
        User user = dtoToUser(userDTO);
        ApiResult result = educationMSService.addUser(user);
        return ResponseEntity.ok(result);
    }

    // 获取所有学生
    @GetMapping("/students")
    public ResponseEntity<ApiResult> getAllStudents() {
        ApiResult result = educationMSService.searchUser("", UserRole.ROLE_STUDENT);
        return ResponseEntity.ok(result);
    }

    // 获取某个学生信息
    @GetMapping("/students/{userId}")
    public ResponseEntity<ApiResult> getStudentById(@PathVariable int userId) {
        ApiResult result = educationMSService.getPersonalInfo(userId);
        return ResponseEntity.ok(result);
    }

    // 创建学生
    @PostMapping("/students")
    public ResponseEntity<ApiResult> createStudent(@RequestBody StudentDTO studentDTO) {
        User user = studentDtoToUser(studentDTO);
        ApiResult result = educationMSService.addUser(user);
        return ResponseEntity.ok(result);
    }

    // 更新学生
    @PutMapping("/students/{userId}")
    public ResponseEntity<ApiResult> updateStudent(@PathVariable int userId, @RequestBody StudentDTO studentDTO) {
        PersonalInfo info = studentDtoToPersonalInfo(studentDTO);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    // 获取教师信息
    @GetMapping("/teachers/{userId}")
    public ResponseEntity<ApiResult> getTeacherById(@PathVariable int userId) {
        ApiResult result = educationMSService.getPersonalInfo(userId);
        return ResponseEntity.ok(result);
    }

    // 创建教师
    @PostMapping("/teachers")
    public ResponseEntity<ApiResult> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        User user = teacherDtoToUser(teacherDTO);
        ApiResult result = educationMSService.addUser(user);
        return ResponseEntity.ok(result);
    }

    // 更新教师
    @PutMapping("/teachers/{userId}")
    public ResponseEntity<ApiResult> updateTeacher(@PathVariable int userId, @RequestBody TeacherDTO teacherDTO) {
        PersonalInfo info = teacherDtoToPersonalInfo(teacherDTO);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    // 创建管理员
    @PostMapping("/administrators")
    public ResponseEntity<ApiResult> createAdmin(@RequestBody AdministratorDTO adminDTO) {
        User user = adminDtoToUser(adminDTO);
        ApiResult result = educationMSService.addUser(user);
        return ResponseEntity.ok(result);
    }

    // --- DTO 转实体类工具方法（示例） ---
    private User dtoToUser(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        // ... 其他字段
        return user;
    }

    private User studentDtoToUser(StudentDTO dto) {
        // 同上
        return dtoToUser(dto);
    }

    private User teacherDtoToUser(TeacherDTO dto) {
        // 同上
        return dtoToUser(dto);
    }

    private User adminDtoToUser(AdministratorDTO dto) {
        // 同上
        return dtoToUser(dto);
    }

    private PersonalInfo studentDtoToPersonalInfo(StudentDTO dto) {
        PersonalInfo info = new PersonalInfo();
        info.setName(dto.getName());
        info.setEmail(dto.getEmail());
        // ... 其他字段
        return info;
    }

    private PersonalInfo teacherDtoToPersonalInfo(TeacherDTO dto) {
        PersonalInfo info = new PersonalInfo();
        info.setName(dto.getName());
        info.setEmail(dto.getEmail());
        return info;
    }
}