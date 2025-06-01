package com.infoa.educationms.service;

import com.infoa.educationms.DTO.ClassroomDTO;
import com.infoa.educationms.DTO.ClassroomUpdateDTO;
import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.TeacherDTO;
import com.infoa.educationms.entities.Classroom;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CourseArrangementService {
    //获取所有课程信息
    List<CourseDTO> getAllCourses();

    //获取所有教室信息
    List<ClassroomDTO> getAllClassrooms();

    //获取所有教师信息
    List<TeacherDTO> getAllTeachers();

    /**
     * 添加新教室
     *
     * @param classroom 教室信息
     */
    void addClassroom(ClassroomDTO classroom);

    /**
     * 更新教室信息
     *
     * @param classroomId 教室ID
     * @param classroom   更新后的教室信息
     */
    void updateClassroom(Integer classroomId, ClassroomUpdateDTO classroom);

    /**
     * 删除教室
     * @param classroomId 教室ID
     */
    void deleteClassroom(Integer classroomId);

    /**
     * 根据教室ID获取教室信息
     * @param classroomId 教室ID
     * @return 教室信息
     */
    Classroom getClassroomById(Integer classroomId);


}