package com.infoa.educationms.service;

import org.springframework.stereotype.Service;

public interface CourseResourceService {

    //设置作业成绩占比
    void setHomeworkProportion(Integer courseId, Double proportion);

    //设置作业成绩
    void setHomeworkScore(Integer courseId, Integer studentId, Integer score);

    //设置考勤成绩占比
    void setAttendanceProportion(Integer courseId, Double proportion);

    //设置考勤成绩
    void setAttendanceScore(Integer courseId, Integer studentId, Integer score);
}
