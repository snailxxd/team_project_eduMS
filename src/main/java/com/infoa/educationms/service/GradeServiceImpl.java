package com.infoa.educationms.service;

import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
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
        PersonalInfor personalInfo = personalInfoRepository.findOneByPersonalInfoId(user.getPersonalInfoId());

        for (Take take : takes) {
            List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
            Section section = sectionRepository.findOneBySectionId(take.getSectionId());
            Course course = courseRepository.findByCourseId(section.getCourseId());
            for (Grade grade : grades) {
                GradeDTO gradeDTO = new GradeDTO();
                gradeDTO.setGradeId(grade.getGradeId());
                gradeDTO.setGrade(grade.getGrade());
                gradeDTO.setTakesId(take.getTakeId());
                gradeDTO.setProportion(grade.getProportion());
                gradeDTO.setType(grade.getGradeType());
                gradeDTO.setStudentId(studentId);
                gradeDTO.setStudentName(personalInfo.getName());
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

    public List<StudentGradeDTO> getAllStudentGrades(Integer studentId){
        List<Take> takes = takeRepository.findByStudentId(studentId);
        List<StudentGradeDTO> studentGradeDTOS = new ArrayList<>();
        for (Take take : takes) {
            Section section = sectionRepository.findOneBySectionId(take.getSectionId());
            Course course = courseRepository.findByCourseId(section.getCourseId());
            List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());

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

    public List<GradeStatusDTO> getAllStudentGradesBySection(Integer teacherId){
        List<Section> sections = sectionRepository.findByTeacherId(teacherId);
        List<GradeStatusDTO> gradestaetusDTOs = new ArrayList<>();
        for (Section section : sections) {
            Course course = courseRepository.findByCourseId(section.getCourseId());
            List<Take> takes = takeRepository.findBySectionId(section.getSectionId());
            for (Take take : takes) {
                GradeStatusDTO gradestatusDTO = new GradeStatusDTO();
                User user = userRepository.findOneByUserId(take.getStudentId());
                PersonalInfor personalInfo = personalInfoRepository.findOneByPersonalInfoId(user.getPersonalInfoId());

                List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
                double sum = 0;
                for (Grade grade : grades) {
                    gradestatusDTO.setStatus("已确认");
                    sum += grade.getProportion()*grade.getGrade();

                    if(gradeChangeRepository.findByGradeId(grade.getGradeId()).isEmpty()){
                        gradestatusDTO.setStatus("待审核");
                    }
                    else {
                        if(!gradeChangeRepository.findResultByGradeId(grade.getGradeId()).isEmpty()){
                            List<Boolean> results = gradeChangeRepository.findResultByGradeId(grade.getGradeId());
                            if(results.get(0) == true){
                                gradestatusDTO.setStatus("已修改");
                            }
                            else{
                                gradestatusDTO.setStatus("已拒绝");
                            }
                        }
                    }

                    Integer i = (int)sum;
                    gradestatusDTO.setGrade(i);
                    gradestatusDTO.setStudentId(take.getStudentId());
                    gradestatusDTO.setId(Integer.toString(grade.getGradeId()));
                    gradestatusDTO.setStudentName(personalInfo.getName());
                    gradestatusDTO.setCourseId(section.getCourseId());
                    gradestatusDTO.setCourseName(course.getTitle());
                    gradestaetusDTOs.add(gradestatusDTO);
                }
            }
        }
        return gradestaetusDTOs;
    }
}

