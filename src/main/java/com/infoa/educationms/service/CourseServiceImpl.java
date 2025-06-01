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
    private SectionRepository sectionRepository;

    @Autowired
    private TakeRepository takeRepository;

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
                    Course course = section.getCourse();  // 假设 Section 中有 getCourse()

                    if (course == null) return null; // 防御性判断

                    CourseDTO dto = new CourseDTO();
                    dto.setCourseId(course.getCourseId());
                    dto.setTitle(course.getTitle());
                    dto.setDeptName(course.getDeptName());
                    dto.setCredits(course.getCredits());
                    dto.setCourseIntroduction(course.getCourseIntroduction());
                    dto.setCapacity(course.getCapacity());
                    dto.setRequiredRoomType(course.getRequiredRoomType());
                    dto.setGradeYear(course.getGradeYear());
                    dto.setPeriod(course.getPeriod());

                    return dto;
                })
                .filter(Objects::nonNull) // 过滤掉 null 的项
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO addCourse(Section section) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可添加开课信息");
        }
        section.setTeacherId(getCurrentUserId());
        sectionRepository.save(section);
        return new CourseDTO(section);
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
        sectionRepository.save(existing);
        return new CourseDTO(existing);
    }

    @Override
    public void deleteCourse(int courseId) {
        if (!isTeacher()) {
            throw new SecurityException("仅教师可删除开课信息");
        }
        Section section = sectionRepository.findById(courseId).orElse(null);
        if (section == null || section.getTeacherId() != getCurrentUserId()) {
            throw new SecurityException("无权删除该课程");
        }
        if (!takeRepository.findBySectionId(courseId).isEmpty()) {
            throw new IllegalStateException("存在关联选课记录，无法删除");
        }
        sectionRepository.delete(section);
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
        List<Grade> grades = gradeRepository.findBySectionId(sectionId);
        double average = grades.stream().mapToInt(Grade::getGrade).average().orElse(0);
        Map<String, Long> distribution = grades.stream()
                .collect(Collectors.groupingBy(Grade::getGradeType, Collectors.counting()));
        return new CourseStatsDTO(average, distribution);
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
        return grades.stream()
                .sorted((g1, g2) -> Integer.compare(g2.getGrade(), g1.getGrade()))
                .map(g -> new StudentRankDTO(g.getTakeId(), g.getGrade()))
                .collect(Collectors.toList());
    }
}