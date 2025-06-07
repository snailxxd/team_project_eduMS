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
import java.util.Arrays;
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
        List<GradeDTO> gradeDTOs = new ArrayList<>();
        List<Take> takes= takeRepository.findByStudentId(studentId);
        User user = userRepository.findOneByUserId(studentId);
        // 检查user是否为null
        if (user == null) {
            // 用户未找到，可以记录日志并返回空列表，或抛出异常
            // 例如: log.warn("User not found with studentId: {}", studentId);
            return gradeDTOs; // 返回空列表
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
            if(!gradeRepository.existsByTakeId(take.getTakeId())){
                continue;
            }
            List<Double> gradelist = Arrays.asList(0.0, 0.0, 0.0);
            List<Double> proportionlist = Arrays.asList(0.0, 0.0, 0.0);
            for (Grade grade : grades) {
                switch (grade.getGradeType()) {
                    case "attending" -> {
                        gradelist.set(0, (gradelist.get(0) * proportionlist.get(0) + grade.getProportion() * grade.getGrade()) / (proportionlist.get(0) + grade.getProportion()));
                        proportionlist.set(0, proportionlist.get(0) + grade.getProportion());
                    }
                    case "homework" -> {
                        gradelist.set(1, (gradelist.get(1) * proportionlist.get(1) + grade.getProportion() * grade.getGrade()) / (proportionlist.get(1) + grade.getProportion()));
                        proportionlist.set(1, proportionlist.get(1) + grade.getProportion());
                    }
                    case "test" -> {
                        gradelist.set(2, (gradelist.get(2) * proportionlist.get(2) + grade.getProportion() * grade.getGrade()) / (proportionlist.get(2) + grade.getProportion()));
                        proportionlist.set(2, proportionlist.get(2) + grade.getProportion());
                    }
                }
            }

            GradeDTO gradeDTO1 = new GradeDTO();
            GradeDTO gradeDTO2 = new GradeDTO();
            GradeDTO gradeDTO3 = new GradeDTO();
            gradeDTO1.setCourseId(course.getCourseId());
            gradeDTO2.setCourseId(course.getCourseId());
            gradeDTO3.setCourseId(course.getCourseId());
            gradeDTO1.setTakesId(take.getTakeId());
            gradeDTO2.setTakesId(take.getTakeId());
            gradeDTO3.setTakesId(take.getTakeId());
            gradeDTO1.setStudentId(studentId);
            gradeDTO2.setStudentId(studentId);
            gradeDTO3.setStudentId(studentId);
            String name = "";
            if (personalInfo != null) {
                name = personalInfo.getName();
            }
            gradeDTO1.setStudentName(name);
            gradeDTO2.setStudentName(name);
            gradeDTO3.setStudentName(name);

            gradeDTO1.setCourseName(course.getTitle());
            gradeDTO1.setCourseId(course.getCourseId());
            gradeDTO2.setCourseName(course.getTitle());
            gradeDTO2.setCourseId(course.getCourseId());
            gradeDTO3.setCourseName(course.getTitle());
            gradeDTO3.setCourseId(course.getCourseId());

            gradeDTO1.setGradeId(1);

            gradeDTO1.setProportion(proportionlist.get(0).floatValue());
            gradeDTO1.setGrade(gradelist.get(0).intValue());
            gradeDTO2.setProportion(proportionlist.get(1).floatValue());
            gradeDTO2.setGrade(gradelist.get(1).intValue());
            gradeDTO3.setProportion(proportionlist.get(2).floatValue());
            gradeDTO3.setGrade(gradelist.get(2).intValue());

            gradeDTO1.setType("attending");
            gradeDTO2.setType("homework");
            gradeDTO3.setType("test");

            gradeDTOs.add(gradeDTO1);
            gradeDTOs.add(gradeDTO2);
            gradeDTOs.add(gradeDTO3);
        }
        return gradeDTOs;
    }


    private double convertGradeToGpa(int grade) {
        if (grade >= 95) return 5.0;
        if (grade >= 92) return 4.8;
        if (grade >= 89) return 4.5;
        if (grade >= 86) return 4.2;
        if (grade >= 83) return 3.9;
        if (grade >= 80) return 3.6;
        if (grade >= 77) return 3.3;
        if (grade >= 74) return 3.0;
        if (grade >= 71) return 2.7;
        if (grade >= 68) return 2.4;
        if (grade >= 65) return 2.1;
        if (grade >= 62) return 1.8;
        if (grade >= 60) return 1.5;
        return 0.0;
    }

    @Override
    public List<StudentGradeDTO> getAllStudentGrades(Integer studentId){
        List<Take> takes = takeRepository.findByStudentId(studentId);
        List<StudentGradeDTO> studentGradeDTOS = new ArrayList<>();
        for (Take take : takes) {
            if(!gradeRepository.existsByTakeId(take.getTakeId())){
                continue;
            }
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

                User user = userRepository.findOneByUserId(take.getStudentId());
                PersonalInfor personalInfo = personalInfoRepository.findOneByPersonalInforId(user.getPersonalInfoId());

                List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
                for (Grade grade : grades) {
                    GradeStatusDTO gradestatusDTO = new GradeStatusDTO();
                    gradestatusDTO.setStatus("已确认");

                    if(!gradeChangeRepository.findByGradeId(grade.getGradeId()).isEmpty()){
                        gradestatusDTO.setStatus("待审核");
                        List<Boolean> results = gradeChangeRepository.findResultByGradeId(grade.getGradeId());
                        boolean Isnull = false;
                        for (Boolean result : results) {
                            if(result == null){Isnull = true;}
                        }
                        if(!Isnull){
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

                    gradestatusDTO.setName(grade.getName());
                    gradestatusDTO.setGrade(grade.getGrade());
                    gradestatusDTO.setStudentId(take.getStudentId());
                    gradestatusDTO.setId(Integer.toString(grade.getGradeId()));
                    gradestatusDTO.setStudentName(personalInfo.getName());
                    gradestatusDTO.setCourseId(section.getCourseId());
                    gradestatusDTO.setCourseName(course.getTitle());
                    gradestatusDTOs.add(gradestatusDTO);

                }
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

