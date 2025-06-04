package com.infoa.educationms.DTO;


public class OqTeacherCourseInfoDTO {
    private Integer sectionId;       // sec_id
    private String courseName;       // course.title
    private String semester;         // fall/spring
    private Integer year;
    private String dayOfWeek;        // 根据 time_slot.day 转换为中文：周一/周二...
    private String startTime;        // 格式：HH:mm
    private String endTime;          // 格式：HH:mm

    public OqTeacherCourseInfoDTO() {}

    public OqTeacherCourseInfoDTO(Integer sectionId, String courseName, String semester, Integer year, String dayOfWeek, String startTime, String endTime) {
        this.sectionId = sectionId;
        this.courseName = courseName;
        this.semester = semester;
        this.year = year;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
