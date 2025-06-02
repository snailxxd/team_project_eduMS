package com.infoa.educationms.service;

import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    private int getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    private User getCurrentUser() {
        return null;
    }

    private boolean isTeacher() {
        User user = getCurrentUser();
        return user.getUserType() == UserRole.ROLE_TEACHER;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(course -> {
                    CourseDTO dto = new CourseDTO();
                    dto.setCourseId(course.getCourseId());
                    dto.setTitle(course.getTitle());
                    dto.setDeptName(course.getDeptName());
                    dto.setCredits(course.getCredits());
                    dto.setCourseIntroduction(course.getIntroduction());
                    dto.setCapacity(course.getCapacity());
                    dto.setRequiredRoomType(course.getRequiredRoomType());
                    dto.setGradeYear(course.getGradeYear());
                    dto.setPeriod(course.getPeriod());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO addCourse(CourseDTO dto) {
         // if (!isTeacher()) {
         //  throw new SecurityException("仅教师可添加课程");
         // }

         // 先创建或更新 Course 实体
         Course course = new Course();
         course.setTitle(dto.getTitle());
         course.setDeptName(dto.getDeptName());
         course.setCredits(dto.getCredits());
         course.setIntroduction(dto.getCourseIntroduction());
         course.setCapacity(dto.getCapacity());
         course.setRequiredRoomType(dto.getRequiredRoomType());
         course.setPeriod(dto.getPeriod());
         course.setGradeYear(dto.getGradeYear());

         course = courseRepository.save(course);

         return toCourseDTO(course, course.getRequiredRoomType(), course.getGradeYear(), course.getPeriod());
    }


    @Override
    public CourseDTO updateCourse(int courseId, CourseDTO dto) {
        // 更新 Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("找不到课程ID: " + courseId));

        course.setTitle(dto.getTitle());
        course.setDeptName(dto.getDeptName());
        course.setCredits(dto.getCredits());
        course.setIntroduction(dto.getCourseIntroduction());
        course.setCapacity(dto.getCapacity());
        course.setRequiredRoomType(dto.getRequiredRoomType());
        course.setPeriod(dto.getPeriod());
        course.setGradeYear(dto.getGradeYear());
        courseRepository.save(course);

        return toCourseDTO(course, course.getRequiredRoomType(), course.getGradeYear(), course.getPeriod());
    }


    @Override
    public void deleteCourse(int courseId) {
        // 删除课程对应的所有 Section
        List<Section> sections = sectionRepository.findByCourseId(courseId);

        // 先删除所有开课区段
        sectionRepository.deleteAll(sections);

        // 再删除课程实体
        courseRepository.deleteById(courseId);
    }


    @Override
    public List<CourseStatsDTO>     getCourseStats(Integer teacherId) {

        // 假设通过 section 获取对应的 Course 信息
        List<Section> sections = sectionRepository.findByTeacherId(teacherId);
        List<CourseStatsDTO> dtos = new ArrayList<>();
        for (Section section : sections) {
            Course course = courseRepository.findOneByCourseId(section.getCourseId());
            List<Take> takes = takeRepository.findBySectionId(section.getSectionId());
            List<Integer> scores = new ArrayList<>();
            List<Double> point = new ArrayList<>();
            for (Take take : takes) {
                List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
                double sum = 0;
                for (Grade grade : grades) {
                    sum += grade.getGrade()*grade.getProportion();
                }
                Integer i = (int)sum;
                scores.add(i);
                point.add(convertGradeToGpa(i));
            }

            double totalGpaSum = 0;
            double totalProportion = 0;

            for (Take take : takes) {
                List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());

                double studentWeightedScore = 0;
                double studentWeightedGpa = 0;
                double studentTotalWeight = 0;

                for (Grade grade : grades) {
                    double weight = grade.getProportion();
                    studentWeightedScore += grade.getGrade() * weight;
                    studentWeightedGpa += convertGradeToGpa(grade.getGrade()) * weight;
                    studentTotalWeight += weight;
                }

                if (studentTotalWeight > 0) {
                    scores.add((int)Math.round(studentWeightedScore));
                    totalGpaSum += studentWeightedGpa / studentTotalWeight;
                    totalProportion++;
                }
            }

            double average = scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            double gpa = totalProportion > 0 ? totalGpaSum / totalProportion : 0.0;

            CourseStatsDTO dto = new CourseStatsDTO();
            dto.setCourseId(course.getCourseId());
            dto.setCourseName(course.getTitle());
            dto.setGpa(gpa);
            dto.setAverage(average);
            dto.setTeacherId(teacherId);
            dto.setTotalStudents(takes.size());
            dto.setScores(scores);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<StudentRankDTO> getCourseStudentRanks(int sectionId) {
        List<Grade> grades = gradeRepository.findBySectionId(sectionId);

        // 按学生分组
        Map<Integer, List<Grade>> gradeGroups = grades.stream()
                .collect(Collectors.groupingBy(Grade::getTakeId));

        List<StudentRankDTO> rankList = new ArrayList<>();
        int rank = 1;

        // 将学生分组后按加权总分排序
        List<Map.Entry<Integer, List<Grade>>> sortedEntries = gradeGroups.entrySet().stream()
                .sorted((e1, e2) -> {
                    double total1 = e1.getValue().stream()
                            .mapToDouble(g -> g.getGrade() * g.getProportion())
                            .sum();
                    double total2 = e2.getValue().stream()
                            .mapToDouble(g -> g.getGrade() * g.getProportion())
                            .sum();
                    return Double.compare(total2, total1); // 降序
                })
                .toList();

        for (Map.Entry<Integer, List<Grade>> entry : sortedEntries) {
            Integer takeId = entry.getKey(); // 修正命名
            List<Grade> studentGrades = entry.getValue();

            double totalScore = studentGrades.stream()
                    .mapToDouble(g -> g.getGrade() * g.getProportion())
                    .sum();

            double gpa = studentGrades.stream()
                    .mapToDouble(g -> convertGradeToGpa(g.getGrade()) * g.getProportion())
                    .sum(); // 加权 GPA

            // 通过 takeId 找到 studentId
            Optional<Take> takeOpt = takeRepository.findById(takeId);
            int studentId = takeOpt.map(Take::getStudentId).orElse(-1);

            String studentName = "未知";
            if (studentId != -1) {
                studentName = studentRepository.findById(studentId)
                        .map(student -> personalInfoRepository.findById(student.getPersonalInfoId())
                                .map(PersonalInfor::getName)
                                .orElse("未知"))
                        .orElse("未知");
            }

            StudentRankDTO dto = new StudentRankDTO();
            dto.setRank(rank++);
            dto.setStudentId(studentId);
            dto.setStudentName(studentName);
            dto.setScore((int) Math.round(totalScore));
            dto.setGpa(gpa);

            rankList.add(dto);
        }


        return rankList;
    }

    // 这里是示范用的分数转 GPA 方法，你可以替换成自己的逻辑
    private double convertGradeToGpa(int grade) {
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }

    private static CourseDTO toCourseDTO(Course course, String course1, Integer course2, int course3) {
        CourseDTO updatedDto = new CourseDTO();
        updatedDto.setCourseId(course.getCourseId());
        updatedDto.setTitle(course.getTitle());
        updatedDto.setDeptName(course.getDeptName());
        updatedDto.setCredits(course.getCredits());
        updatedDto.setCourseIntroduction(course.getIntroduction());
        updatedDto.setCapacity(course.getCapacity());
        updatedDto.setRequiredRoomType(course1);
        updatedDto.setGradeYear(course2);
        updatedDto.setPeriod(course3);
        return updatedDto;
    }
}
