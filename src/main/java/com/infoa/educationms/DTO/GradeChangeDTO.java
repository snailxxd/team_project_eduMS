package com.infoa.educationms.DTO;
import lombok.Data;

@Data
public class GradeChangeDTO {
    private Integer gradeChangeId;
    private Integer takesId;
    private Integer teacherId;
    private Boolean result;
    private Integer newGrade;
    private String applyTime;
    private String checkTime;
    private Integer gradeId;
    private Integer studentId;
    private String studentName;
    private Integer courseId;
    private String courseName;
    private Integer originalGrade;
    private String reason;

}
