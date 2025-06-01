package com.infoa.educationms.service;

import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.CourseStatsDTO;
import com.infoa.educationms.DTO.StudentRankDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        return userRepository.findByAccountNumber(account)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在：" + account));
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
    public CourseDTO addCourse(Section section) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可添加开课信息");
        }

        section.setTeacherId(getCurrentUserId());
        Section savedSection = sectionRepository.save(section);

        Optional<Course> courseOpt = courseRepository.findById(savedSection.getCourseId());
        if (courseOpt.isEmpty()) {
            throw new IllegalArgumentException("无法找到对应课程ID: " + savedSection.getCourseId());
        }

        Course course = courseOpt.get();

        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDeptName(course.getDeptName());
        dto.setCredits(course.getCredits());
        dto.setCourseIntroduction(course.getIntroduction());
        dto.setCapacity(course.getCapacity());

        dto.setRequiredRoomType("Unknown"); // 可按需设置
        dto.setGradeYear(savedSection.getYear());
        dto.setPeriod(1); // 可从 timeSlotId 中解析

        return dto;
    }


    @Override
    public CourseDTO updateCourse(Section section) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可修改开课信息");
        }

        Section existing = sectionRepository.findById(section.getSectionId()).orElse(null);
        if (existing == null || existing.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权修改他人课程信息");
        }

        existing.setSemester(section.getSemester());
        existing.setYear(section.getYear());
        existing.setClassroomId(section.getClassroomId());
        existing.setTimeSlotIdList(section.getTimeSlotIdList());  // 如果需要更新
        existing.setTeacherId(section.getTeacherId());    // 如果允许更新教师ID

        sectionRepository.save(existing);

        Course course = courseRepository.findById(existing.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("找不到对应课程"));

        return toCourseDTO(existing, course);
    }


    @Override
    public void deleteCourse(int courseId) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可删除开课信息");
        }
        // 找到对应的 Section
        Section section = sectionRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("找不到对应的课程信息"));

        // 判断是否为当前教师
        if (section.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权删除他人课程信息");
        }

        sectionRepository.deleteById(courseId);
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
                            .map(PersonalInfo::getName)
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


    private CourseDTO toCourseDTO(Section section, Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDeptName(course.getDeptName());
        dto.setCredits(course.getCredits());
        dto.setCourseIntroduction(course.getIntroduction());
        dto.setCapacity(course.getCapacity());

        dto.setRequiredRoomType("未知");  // 如果有数据则替换
        dto.setGradeYear(section.getYear());
        dto.setPeriod(1);  // 可根据timeSlotId解析真实课时数

        return dto;
    }

}
