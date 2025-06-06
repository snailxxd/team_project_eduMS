package com.infoa.educationms.service;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.PersonalInfor;
import com.infoa.educationms.entities.Student;

import java.util.List;
import java.util.Map;

public interface OnlineQuizService {
    List<OqCourseForTeacherDTO> getCoursesByTeacher(Integer teacherId);
    List<OqStudentDTO> getStudentsByCourse(Integer courseId);
    List<OqCourseForStudentDTO> getCoursesByStudent(Integer studentId);
    void setExamProportion(Integer courseId, Double proportion);
    void setExamScore(Integer courseId, Integer studentId, Integer score);
    List<Integer> getSectionIdsByCourse(Integer courseId);
    List<OqTimeSlotDTO> getTimeSlotInfoBySection(Integer sectionId);
    List<OqTeacherCourseInfoDTO> getTeacherCourseDetails(Integer teacherId);

}
