package com.infoa.educationms.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sec_id")
    private int sectionId;

    @Column(name = "course_id")
    private int courseId;

    private String semester;
    private int year;

    @Column(name = "classroom_id")
    private int classroomId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "time_slot_ids", columnDefinition = "json")
    private JsonNode timeSlotId;

    @Column(name = "teacher_id")
    private int teacherId;

    private int remain_capacity;

    // constructors
    public Section() {}

    public Section(int sectionId, int courseId, String semester, int year, int classroomId, JsonNode timeSlotId, int teacherId, int time) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.semester = semester;
        this.year = year;
        this.classroomId = classroomId;
        this.timeSlotId = timeSlotId;
        this.teacherId = teacherId;
    }

    // 转换为字符串
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", courseId=" + courseId +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", classroomId=" + classroomId +
                ", timeSlotId=" + timeSlotId +
                ", teacherId=" + teacherId +
                '}';
    }

    // getter and setter methods
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    // 新增方法：获取时间槽ID列表
    public List<Integer> getTimeSlotIdList() {
        if (timeSlotId != null && timeSlotId.isArray()) {
            List<Integer> ids = new ArrayList<>();
            for (JsonNode node : timeSlotId) {
                ids.add(node.asInt());
            }
            return ids;
        }
        return Collections.emptyList();
    }

    // 新增方法：设置时间槽ID列表
    public void setTimeSlotIdList(List<Integer> ids) {
        this.timeSlotId = new ObjectMapper().valueToTree(ids);
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}