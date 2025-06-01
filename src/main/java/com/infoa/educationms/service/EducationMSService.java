package com.infoa.educationms.service;

import com.infoa.educationms.entities.User;
import com.infoa.educationms.entities.UserRole;

import java.util.List;

import com.infoa.educationms.entities.Grade;
import com.infoa.educationms.entities.PersonalInfor;
import com.infoa.educationms.entities.Section;
import com.infoa.educationms.queries.ApiResult;

public interface EducationMSService {
    // 通用模块接口
    ApiResult getPersonalInfo(int userId);               // 查询个人信息
    ApiResult updatePersonalInfo(int userId, PersonalInfor info);  // 更新个人信息和头像
    ApiResult querySection();    // 查询课程信息

    // 学生模块接口
    ApiResult queryGrade();
    ApiResult analyzeGradeStu();  // 成绩分析

    // 管理员模块接口
    ApiResult addUser(User user);   // 添加用户
    ApiResult deleteUser(); // 删除用户
    ApiResult searchUser(String keyword, UserRole role);  // 查询用户
    ApiResult updateUser();  // 更新用户
    ApiResult auditGradeChange(int changeId, boolean isApproved); // 审核成绩变更
    ApiResult getAllTeachersAndStudents(); // 获取全体师生信息
    ApiResult queryDepartment(); // 查询所有部门信息

    // 教师模块接口
    ApiResult addSection(Section section);   // 添加开课信息
    ApiResult deleteSection(int sectionId); // 删除开课信息
    ApiResult updateSection(Section section); // 更新开课信息
    ApiResult submitGrade(List<Grade> grades);    // 提交成绩
    ApiResult changeGrade(int takeId, int newGrade, String reason);    // 修改成绩
    ApiResult analyzeGradeTeacher(int sectionId);  // 成绩分析

}
