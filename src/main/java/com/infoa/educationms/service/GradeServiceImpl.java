package com.infoa.educationms.service;

import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
import com.infoa.educationms.DTO.OutGradeDTO;
import com.infoa.educationms.DTO.StudentGradeDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private GradeChangeRepository gradeChangeRepository;


    @Override
    public List<GradeDTO> getAllGrades(Integer studentId){
        List<Take> takes= takeRepository.findByStudentId(studentId);
        List<GradeDTO> gradeDTOS = new ArrayList<>();

        User user = userRepository.findOneByUserId(studentId);
        // 检查user是否为null
        if (user == null) {
            // 用户未找到，可以记录日志并返回空列表，或抛出异常
            // 例如: log.warn("User not found with studentId: {}", studentId);
            return gradeDTOS; // 返回空列表
        }

        PersonalInfor personalInfo = personalInfoRepository.findOneByPersonalInforId(user.getPersonalInfoId());
        // 同样检查personalInfo是否为null，以避免后续的NullPointerException
        if (personalInfo == null) {
            // 个人信息未找到，可以记录日志
            // 例如: log.warn("PersonalInfo not found for user: {}", studentId);
            // 根据业务需求决定如何处理：
            // 1. 返回空列表或部分填充的DTO（如果允许）
            // 2. 抛出异常
            // 此处选择继续，但学生姓名将无法设置，或者您可以选择返回
            // return gradeDTOS;
        }

        for (Take take : takes) {
            List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
            Section section = sectionRepository.findOneBySectionId(take.getSectionId());
            Course course = courseRepository.findOneByCourseId(section.getCourseId()); // 建议也检查section和course是否为null

            if (section == null || course == null) {
                // log.warn("Section or Course not found for takeId: {}", take.getTakeId());
                continue; // 跳过这个take记录
            }

            for (Grade grade : grades) {
                GradeDTO gradeDTO = new GradeDTO();
                gradeDTO.setGradeId(grade.getGradeId());
                gradeDTO.setGrade(grade.getGrade());
                gradeDTO.setTakesId(take.getTakeId());
                gradeDTO.setProportion(grade.getProportion());
                gradeDTO.setType(grade.getGradeType());
                gradeDTO.setStudentId(studentId);
                // 只有在personalInfo不为null时才设置学生姓名
                if (personalInfo != null) {
                    gradeDTO.setStudentName(personalInfo.getName());
                } else {
                    gradeDTO.setStudentName("未知学生"); // 或者其他默认值
                }
                gradeDTO.setCourseName(course.getTitle());
                gradeDTO.setCourseId(course.getCourseId());
                gradeDTOS.add(gradeDTO);
            }
        }
        return gradeDTOS;
    }


    private double convertGradeToGpa(int grade) {
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }
    @Override
    public List<StudentGradeDTO> getAllStudentGrades(Integer studentId){
        List<Take> takes = takeRepository.findByStudentId(studentId);
        List<StudentGradeDTO> studentGradeDTOS = new ArrayList<>();
        for (Take take : takes) {
            Section section = sectionRepository.findOneBySectionId(take.getSectionId());
            Course course = courseRepository.findOneByCourseId(section.getCourseId());
            List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());

            if (grades.isEmpty()) {
                continue;
            }
            double sum = 0;
            for (Grade grade : grades) {
                sum += grade.getProportion()*grade.getGrade();
            }
            StudentGradeDTO studentGradeDTO = new StudentGradeDTO();
            studentGradeDTO.setCourseName(course.getTitle());
            studentGradeDTO.setSemester(section.getSemester());
            Integer i = (int)sum;
            studentGradeDTO.setScore(i);
            studentGradeDTO.setCourseId(course.getCourseId());
            studentGradeDTO.setCredits(course.getCredits());
            studentGradeDTO.setGpa(convertGradeToGpa(i));
            studentGradeDTOS.add(studentGradeDTO);
        }
        return studentGradeDTOS;
    }
    @Override
    public List<GradeStatusDTO> getAllStudentGradesBySection(Integer teacherId){
        List<Section> sections = sectionRepository.findByTeacherId(teacherId);
        List<GradeStatusDTO> gradestatusDTOs = new ArrayList<>();
        for (Section section : sections) {
            Course course = courseRepository.findOneByCourseId(section.getCourseId());
            List<Take> takes = takeRepository.findBySectionId(section.getSectionId());
            for (Take take : takes) {
                GradeStatusDTO gradestatusDTO = new GradeStatusDTO();
                User user = userRepository.findOneByUserId(take.getStudentId());
                PersonalInfor personalInfo = personalInfoRepository.findOneByPersonalInforId(user.getPersonalInfoId());

                List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
                double sum = 0;
                for (Grade grade : grades) {
                    gradestatusDTO.setStatus("已确认");
                    sum += grade.getProportion()*grade.getGrade();

                    if(!gradeChangeRepository.findByGradeId(grade.getGradeId()).isEmpty()){
                        gradestatusDTO.setStatus("待审核");
                        List<Boolean> results = gradeChangeRepository.findResultByGradeId(grade.getGradeId());
                        boolean Isnull = false;
                        for (Boolean result : results) {
                            if(result == null){Isnull = true;}
                        }
                        if(Isnull){
                        }
                        else {
                            if(!results.isEmpty()){


                                if(results.get(0) == true){
                                    gradestatusDTO.setStatus("已修改");
                                }
                                else{
                                    gradestatusDTO.setStatus("已拒绝");
                                }
                            }
                        }
                    }


                }
                Integer i = (int)sum;
                gradestatusDTO.setGrade(i);
                gradestatusDTO.setStudentId(take.getStudentId());
                gradestatusDTO.setId(Integer.toString(section.getCourseId()));
                gradestatusDTO.setStudentName(personalInfo.getName());
                gradestatusDTO.setCourseId(section.getCourseId());
                gradestatusDTO.setCourseName(course.getTitle());
                gradestatusDTOs.add(gradestatusDTO);
            }
        }
        return gradestatusDTOs;
    }

    @Override
    public void addGrade(List<OutGradeDTO> outGradeDTOs){
        for (OutGradeDTO outGradeDTO : outGradeDTOs) {
            Grade grade = new Grade();
            grade.setId(outGradeDTO.getId());
            grade.setGradeType(outGradeDTO.getType());
            grade.setGrade(outGradeDTO.getGrade());
            grade.setProportion(outGradeDTO.getProportion());
            grade.setName(outGradeDTO.getName());
            Take take = takeRepository.findOneBySectionIdAndStudentId(outGradeDTO.getSecId(),outGradeDTO.getStudentId());
            grade.setTakeId(take.getTakeId());
            gradeRepository.save(grade);
        }
    }

}

