package com.infoa.educationms.service;

import com.infoa.educationms.DTO.OqCourseForStudentDTO;
import com.infoa.educationms.DTO.OqCourseForTeacherDTO;
import com.infoa.educationms.DTO.OqStudentDTO;
import com.infoa.educationms.entities.Course;

import java.util.List;

public interface OnlineQuizService {
    /**
     * 查询老师教授课程
     * @param teacherId 教师ID
     * @return 课程列表
     */
    List<OqCourseForTeacherDTO> getCoursesByTeacher(Integer teacherId);

    /**
     * 查询课程所有学生
     * @param courseId 课程ID
     * @return 学生列表
     */
    List<OqStudentDTO> getStudentsByCourse(Integer courseId);

    /**
     * 查询学生所有参加的课程
     * @param studentId 学生ID
     * @return 课程列表
     */
    List<OqCourseForStudentDTO> getCoursesByStudent(Integer studentId);

    /**
     * 设置考试成绩占比
     * @param courseId 课程ID
     * @param proportion 占比(0-1之间)
     */
    void setExamProportion(Integer courseId, Double proportion);

    /**
     * 设置考试成绩
     * @param courseId 课程ID
     * @param studentId 学生ID
     * @param score 分数(0-100)
     */
    void setExamScore(Integer courseId, Integer studentId, Integer score);
}