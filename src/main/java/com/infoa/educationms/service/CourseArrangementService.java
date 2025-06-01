package com.infoa.educationms.service;

import com.infoa.educationms.DTO.CaClassroomDTO;
import com.infoa.educationms.DTO.CaClassroomUpdateDTO;
import com.infoa.educationms.DTO.CaCourseDTO;
import com.infoa.educationms.DTO.CaTeacherDTO;
import com.infoa.educationms.entities.Classroom;

import java.util.List;

public interface CourseArrangementService {
    //获取所有课程信息
    List<CaCourseDTO> getAllCourses();

    //获取所有教室信息
    List<CaClassroomDTO> getAllClassrooms();

    //获取所有教师信息
    List<CaTeacherDTO> getAllTeachers();

    /**
     * 添加新教室
     *
     * @param classroom 教室信息
     */
    void addClassroom(CaClassroomDTO classroom);

    /**
     * 更新教室信息
     *
     * @param classroomId 教室ID
     * @param classroom   更新后的教室信息
     */
    void updateClassroom(Integer classroomId, CaClassroomUpdateDTO classroom);

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