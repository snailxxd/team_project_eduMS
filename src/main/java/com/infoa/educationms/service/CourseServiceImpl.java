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
    private StudentRepository studentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private UserRepository userRepository;

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
        List<Section> sections = sectionRepository.findAll();

        return sections.stream()
                .map(section -> {
                    // 根据 section 中的 courseId 查找 Course 实体
                    Optional<Course> courseOpt = courseRepository.findById(section.getCourseId());
                    if (courseOpt.isEmpty()) return null;

                    Course course = courseOpt.get();

                    CourseDTO dto = new CourseDTO();
                    dto.setCourseId(course.getCourseId());
                    dto.setTitle(course.getTitle());
                    dto.setDeptName(course.getDeptName());
                    dto.setCredits(course.getCredits());
                    dto.setCourseIntroduction(course.getIntroduction());
                    dto.setCapacity(course.getCapacity());

                    // 从 Section 获取剩余字段
                    dto.setRequiredRoomType("Unknown"); // 假设没有直接字段就占位
                    dto.setGradeYear(section.getYear());
                    dto.setPeriod(1); // 待实现

                    return dto;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    @Override
    public CourseDTO addCourse(CourseDTO dto) {
         if (!isTeacher()) {
           throw new SecurityException("仅教师可添加课程");
         }

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

        CourseDTO dto2 = new CourseDTO();
        dto2.setCourseId(course.getCourseId());
        dto2.setTitle(course.getTitle());
        dto2.setDeptName(course.getDeptName());
        dto2.setCredits(course.getCredits());
        dto2.setCourseIntroduction(course.getIntroduction());
        dto2.setCapacity(course.getCapacity());
        dto2.setRequiredRoomType(course.getRequiredRoomType());
        dto2.setGradeYear(course.getGradeYear());
        dto2.setPeriod(course.getPeriod());

        return dto2;
    }


    @Override
    public CourseDTO updateCourse(int courseId, CourseDTO dto) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可修改课程");
        }

        // 更新 Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("找不到课程ID: " + courseId));

        course.setTitle(dto.getTitle());
        course.setDeptName(dto.getDeptName());
        course.setCredits(dto.getCredits());
        course.setIntroduction(dto.getCourseIntroduction());
        course.setCapacity(dto.getCapacity());
        courseRepository.save(course);

        // 更新对应 Section（这里假设只有一个 Section）
        Section section = sectionRepository.findFirstByCourseId(courseId)
                .orElseThrow(() -> new IllegalArgumentException("找不到对应开课区段"));

        if (section.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权修改他人课程信息");
        }

        section.setYear(dto.getGradeYear());
        section.setSemester(""); // 根据 dto 补充
        // 其它 Section 字段根据 dto 补充
        sectionRepository.save(section);

        return null;
    }


    @Override
    public void deleteCourse(int courseId) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可删除课程");
        }

        // 删除课程对应的所有 Section
        List<Section> sections = sectionRepository.findByCourseId(courseId);

        for (Section section : sections) {
            if (section.getTeacherId() != getCurrentUserId()) {
                throw new SecurityException("无权删除他人课程信息");
            }
        }

        // 先删除所有开课区段
        sectionRepository.deleteAll(sections);

        // 再删除课程实体
        courseRepository.deleteById(courseId);
    }


    @Override
    public CourseStatsDTO getCourseStats(int sectionId) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可查看课程统计信息");
        }
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (section == null || section.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权查看该课程");
        }

        // 假设通过 section 获取对应的 Course 信息
        Course course = courseRepository.findById(section.getCourseId()).orElse(null);
        if (course == null) {
            throw new IllegalStateException("课程不存在");
        }

        List<Grade> grades = gradeRepository.findBySectionId(sectionId);
        int totalStudents = grades.size();

        // 计算平均分
        double average = grades.stream()
                .mapToInt(Grade::getGrade)
                .average()
                .orElse(0);

        // 这里假设你有方法计算 GPA，比如 convertGradeToGpa
        double gpa = grades.stream()
                .mapToDouble(g -> convertGradeToGpa(g.getGrade()))
                .average()
                .orElse(0);

        // 收集所有成绩
        List<Integer> scores = grades.stream()
                .map(Grade::getGrade)
                .toList();

        CourseStatsDTO dto = new CourseStatsDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getTitle());
        dto.setAverage(average);
        dto.setGpa(gpa);
        dto.setTotalStudents(totalStudents);
        dto.setScores(scores);

        return dto;
    }

    @Override
    public List<StudentRankDTO> getCourseStudentRanks(int sectionId) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可查看学生成绩排名");
        }
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (section == null || section.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权查看该课程学生成绩");
        }

        List<Grade> grades = gradeRepository.findBySectionId(sectionId);

        // 按成绩降序排序
        List<Grade> sortedGrades = grades.stream()
                .sorted((g1, g2) -> Integer.compare(g2.getGrade(), g1.getGrade()))
                .toList();

        // 给学生排名
        List<StudentRankDTO> rankList = new ArrayList<>();
        int rank = 1;
        for (Grade g : sortedGrades) {
            StudentRankDTO dto = new StudentRankDTO();
            dto.setRank(rank++);
            dto.setStudentId(g.getTakeId());

            String studentName = studentRepository.findById(g.getTakeId())
                    .map(student -> personalInfoRepository.findById(student.getPersonalInfoId())
                            .map(PersonalInfor::getName)
                            .orElse("未知"))
                    .orElse("未知");


            dto.setStudentName(studentName);
            dto.setScore(g.getGrade());
            dto.setGpa(convertGradeToGpa(g.getGrade()));

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


}
