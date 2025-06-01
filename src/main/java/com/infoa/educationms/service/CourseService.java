package com.infoa.educationms.service;


import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;
import com.infoa.educationms.entities.Section;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseDTO addCourse(Section section);
    CourseDTO updateCourse(Section section);
    void deleteCourse(int courseId);
    CourseStatsDTO getCourseStats(int sectionId);
    List<StudentRankDTO> getCourseStudentRanks(int sectionId);
}
