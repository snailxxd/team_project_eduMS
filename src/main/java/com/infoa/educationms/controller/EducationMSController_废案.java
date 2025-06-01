package com.infoa.educationms.controller;

import com.infoa.educationms.entities.*;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EducationMSController_废案 {

    private final EducationMSService service;

    @Autowired
    public EducationMSController_废案(EducationMSService service) {
        this.service = service;
    }

    //通用模块接口
    
    /**
     * 查询个人信息
     * @param userId 用户ID
     * @return 包含个人信息的响应
     */
    @GetMapping("/users/{userId}/personal-info")
    public ResponseEntity<ApiResult> getPersonalInfo(@PathVariable int userId) {
        ApiResult result = service.getPersonalInfo(userId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 更新个人信息
     * @param userId 用户ID
     * @param info 个人信息对象
     * @return 操作结果
     */
    @PutMapping("/users/{userId}/personal-info")
    public ResponseEntity<ApiResult> updatePersonalInfo(
            @PathVariable int userId, 
            @RequestBody PersonalInfo info) {
        ApiResult result = service.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 查询所有课程信息
     * @return 包含课程列表的响应
     */
    @GetMapping("/sections")
    public ResponseEntity<ApiResult> getAllSections() {
        ApiResult result = service.querySection();
        return ResponseEntity.ok(result);
    }

    //学生模块接口
    
    /**
     * 查询当前学生成绩
     * @return 包含成绩详情的响应
     */
    @GetMapping("/students/grades")
    public ResponseEntity<ApiResult> getStudentGrades() {
        ApiResult result = service.queryGrade();
        return ResponseEntity.ok(result);
    }
    
    /**
     * 分析当前学生成绩
     * @return 包含成绩分析结果的响应
     */
    @GetMapping("/students/grades/analysis")
    public ResponseEntity<ApiResult> analyzeStudentGrades() {
        ApiResult result = service.analyzeGradeStu();
        return ResponseEntity.ok(result);
    }

    //管理员模块接口
    
    /**
     * 添加新用户
     * @param user 用户对象
     * @return 操作结果
     */
    @PostMapping("/admin/users")
    public ResponseEntity<ApiResult> addUser(@RequestBody User user) {
        ApiResult result = service.addUser(user);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 删除当前管理员用户
     * @return 操作结果
     */
    @DeleteMapping("/admin/users")
    public ResponseEntity<ApiResult> deleteAdminUser() {
        ApiResult result = service.deleteUser();
        return ResponseEntity.ok(result);
    }
    
    /**
     * 搜索用户
     * @param keyword 搜索关键字
     * @param role 用户角色
     * @return 包含用户列表的响应
     */
    @GetMapping("/admin/users/search")
    public ResponseEntity<ApiResult> searchUsers(
            @RequestParam String keyword,
            @RequestParam UserRole role) {
        ApiResult result = service.searchUser(keyword, role);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 更新管理员信息
     * @return 操作结果
     */
    @PutMapping("/admin/users")
    public ResponseEntity<ApiResult> updateAdminUser() {
        ApiResult result = service.updateUser();
        return ResponseEntity.ok(result);
    }
    
    /**
     * 审核成绩修改申请
     * @param changeId 修改申请ID
     * @param isApproved 是否批准
     * @return 操作结果
     */
    @PostMapping("/admin/grade-changes/{changeId}/audit")
    public ResponseEntity<ApiResult> auditGradeChange(
            @PathVariable int changeId,
            @RequestParam boolean isApproved) {
        ApiResult result = service.auditGradeChange(changeId, isApproved);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取全体师生用户信息
     * @return 包含全体师生信息的响应
     */
    @GetMapping("/admin/users/all-teachers-students")
    public ResponseEntity<ApiResult> getAllTeachersAndStudents() {
        ApiResult result = service.getAllTeachersAndStudents();
        return ResponseEntity.ok(result);
    }

    //教师模块接口
    
    /**
     * 添加开课信息
     * @param section 开课信息对象
     * @return 操作结果
     */
    @PostMapping("/teachers/sections")
    public ResponseEntity<ApiResult> addSection(@RequestBody Section section) {
        ApiResult result = service.addSection(section);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 删除开课信息
     * @param sectionId 开课信息ID
     * @return 操作结果
     */
    @DeleteMapping("/teachers/sections/{sectionId}")
    public ResponseEntity<ApiResult> deleteSection(@PathVariable int sectionId) {
        ApiResult result = service.deleteSection(sectionId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 更新开课信息
     * @param section 开课信息对象
     * @return 操作结果
     */
    @PutMapping("/teachers/sections")
    public ResponseEntity<ApiResult> updateSection(@RequestBody Section section) {
        ApiResult result = service.updateSection(section);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 提交成绩
     * @param grades 成绩列表
     * @return 操作结果
     */
    @PostMapping("/teachers/grades")
    public ResponseEntity<ApiResult> submitGrades(@RequestBody List<Grade> grades) {
        ApiResult result = service.submitGrade(grades);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 申请修改成绩
     * @param takeId 选课ID
     * @param newGrade 新成绩
     * @param reason 修改原因
     * @return 操作结果
     */
    @PostMapping("/teachers/grades/change-request")
    public ResponseEntity<ApiResult> requestGradeChange(
            @RequestParam int takeId,
            @RequestParam int newGrade,
            @RequestParam String reason) {
        ApiResult result = service.changeGrade(takeId, newGrade, reason);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 分析课程成绩
     * @param sectionId 开课信息ID
     * @return 包含成绩分析结果的响应

    @GetMapping("/teachers/sections/{sectionId}/grades/analysis")
    public ResponseEntity<ApiResult> analyzeCourseGrades(@PathVariable int sectionId) {
        ApiResult result = service.analyzeGradeTeacher(sectionId);
        return ResponseEntity.ok(result);
    }
     */

    @GetMapping("/teachers/sections/{sectionId}/grades/analysis")
    public ResponseEntity<ApiResult> analyzeCourseGrades(@PathVariable int sectionId) {
        ApiResult result = service.analyzeGradeTeacher(sectionId);
        return ResponseEntity.ok(result);
    }

}