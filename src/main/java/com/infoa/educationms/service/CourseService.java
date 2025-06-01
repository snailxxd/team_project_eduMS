package com.infoa.educationms.service;

import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();

    // 使用 CourseDTO 作为参数，返回新增后的 CourseDTO
    CourseDTO addCourse(CourseDTO courseDTO);

    // 修改课程，参数为 CourseDTO
    CourseDTO updateCourse(int courseId, CourseDTO courseDTO);

    // 删除课程，参数为课程ID
    void deleteCourse(int courseId);

    // 获取课程统计，参数为sectionId或课程ID，业务自行调整
    CourseStatsDTO getCourseStats(int sectionId);

    // 获取某课程学生排名
    List<StudentRankDTO> getCourseStudentRanks(int sectionId);
}
