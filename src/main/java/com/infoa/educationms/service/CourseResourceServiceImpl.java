package com.infoa.educationms.service;

import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.Grade;
import com.infoa.educationms.entities.Section;
import com.infoa.educationms.entities.Take;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseResourceServiceImpl implements CourseResourceService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Override
    public void setHomeworkProportion(Integer courseId, Double proportion) {
        validateWeight(proportion);
        updateGradeProportionByType(courseId, "homework", proportion);
    }

    @Override
    public void setHomeworkScore(Integer courseId, Integer studentId, Integer score) {
        validateScore(score);
        setGradeByType(courseId, studentId, "homework", score);
    }

    @Override
    public void setAttendanceProportion(Integer courseId, Double proportion) {
        validateWeight(proportion);
        updateGradeProportionByType(courseId, "attendance", proportion);
    }

    @Override
    public void setAttendanceScore(Integer courseId, Integer studentId, Integer score) {
        validateScore(score);
        setGradeByType(courseId, studentId, "attendance", score);
    }

    private void validateWeight(Double weight) {
        if (weight == null || weight < 0 || weight > 1) {
            throw new IllegalArgumentException("占比必须在0到1之间");
        }
    }

    private void validateScore(Integer score) {
        if (score == null || score < 0 || score > 100) {
            throw new IllegalArgumentException("分数必须在0到100之间");
        }
    }
    private void updateGradeProportionByType(Integer courseId, String gradeType, Double proportion) {
        List<Section> sections = sectionRepository.findByCourseId(courseId);
        List<Integer> sectionIds = sections.stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());

        if (sectionIds.isEmpty()) {
            return;
        }

        List<Grade> grades = gradeRepository.findBySectionIdInAndGradeType(sectionIds, gradeType);
        grades.forEach(grade -> {
            grade.setProportion(proportion.floatValue());
            gradeRepository.save(grade);
        });
    }

    private void setGradeByType(Integer courseId, Integer studentId, String gradeType, Integer score) {
        List<Section> sections = sectionRepository.findByCourseId(courseId);
        List<Integer> sectionIds = sections.stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在"));
        if (sectionIds.isEmpty()) {
            throw new IllegalArgumentException("课程不存在或没有开设section");
        }

        Optional<Take> take = takeRepository.findByStudentIdAndSectionIdIn(
                studentId, sectionIds);

        Grade grade = gradeRepository.findByTakeIdAndGradeType(take.get().getTakeId(), gradeType)
                .orElse(new Grade());

        grade.setGradeType(gradeType);
        grade.setTakeId(take.get().getTakeId());
        grade.setName(course.getTitle());
        grade.setGrade(score);
        grade.setProportion(grade.getProportion());

        gradeRepository.save(grade);
    }
}
