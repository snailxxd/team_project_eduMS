package service;

import entities.*;
import repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OnlineQuizServiceImpl implements OnlineQuizService {


    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;
    private SectionRepository sectionRepository;
    private TakeRepository takeRepository;


    @Override
    public List<Course> getCoursesByTeacher(Integer teacherId) {
        // 1. 查询教师教授的所有section
        List<Section> sections = sectionRepository.findByTeacherId(teacherId);

        // 2. 提取不重复的courseId列表
        List<Integer> courseIds = sections.stream()
                .map(Section::getCourseId)
                .distinct()
                .collect(Collectors.toList());

        // 3. 查询课程基本信息
        return courseRepository.findAllById(courseIds).stream()
                .map(course -> {
                    Course simplified = new Course();
                    simplified.setCourseId(course.getCourseId());
                    simplified.setTitle(course.getTitle());
                    simplified.setCredits(course.getCredits());
                    return simplified;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getStudentsByCourse(Integer courseId) {
        // 1. 查询课程的所有section
        List<Section> sections = sectionRepository.findByCourseId(courseId);
        if (sections.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 获取sectionId列表
        List<Integer> sectionIds = sections.stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());

        // 3. 查询选修这些section的学生ID
        List<Integer> studentIds = takeRepository.findBySectionIdIn(sectionIds).stream()
                .map(Take::getStudentId)
                .distinct()
                .collect(Collectors.toList());

        // 4. 查询学生详细信息
        return studentRepository.findAllById(studentIds).stream()
                .map(student -> {
                    Student simplified = new Student();
                    simplified.setUserId(student.getUserId());
                    simplified.setPersonalInfoId(student.getPersonalInfoId());
                    simplified.setDeptName(student.getDeptName());
                    return simplified;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesByStudent(Integer studentId) {
        // 1. 查询学生的所有选课记录
        List<Take> takes = takeRepository.findByStudentId(studentId);
        if (takes.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 获取sectionId列表
        List<Integer> sectionIds = takes.stream()
                .map(Take::getSectionId)
                .collect(Collectors.toList());

        // 3. 查询这些section对应的课程ID
        List<Integer> courseIds = sectionRepository.findAllById(sectionIds).stream()
                .map(Section::getCourseId)
                .distinct()
                .collect(Collectors.toList());

        // 4. 查询课程详细信息
        return courseRepository.findAllById(courseIds).stream()
                .map(course -> {
                    Course simplified = new Course();
                    simplified.setCourseId(course.getCourseId());
                    simplified.setTitle(course.getTitle());
                    simplified.setCredits(course.getCredits());

                    // 获取授课教师信息（取第一个section的教师）
                    Optional<Section> firstSection = sectionRepository.findFirstByCourseId(course.getCourseId());
                    firstSection.ifPresent(section ->
                            simplified.setDeptName("教师ID: " + section.getTeacherId()));

                    return simplified;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void setExamProportion(Integer courseId, Double proportion) {
        if (proportion == null || proportion < 0 || proportion > 1) {
            throw new IllegalArgumentException("成绩占比必须在0到1之间");
        }

        // 1. 查询课程的所有section
        List<Section> sections = sectionRepository.findByCourseId(courseId);
        List<Integer> sectionIds = sections.stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());

        if (sectionIds.isEmpty()) {
            return;
        }

        // 2. 查询这些section的所有考试成绩记录
        List<Grade> examGrades = gradeRepository.findBySectionIdInAndGradeType(sectionIds, "exam");

        // 3. 更新比例
        examGrades.forEach(grade -> {
            grade.setProportion(proportion.floatValue());
            gradeRepository.save(grade);
        });
    }

    @Override
    public void setExamScore(Integer courseId, Integer studentId, Integer score) {
        if (score == null || score < 0 || score > 100) {
            throw new IllegalArgumentException("分数必须在0到100之间");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在"));
        // 1. 查询课程的所有section
        List<Section> sections = sectionRepository.findByCourseId(courseId);
        List<Integer> sectionIds = sections.stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());

        if (sectionIds.isEmpty()) {
            throw new IllegalArgumentException("课程不存在或没有开设section");
        }

        // 2. 查询学生的选课记录
        Optional<Take> take = takeRepository.findByStudentIdAndSectionIdIn(
                studentId, sectionIds);

        // 3. 设置考试成绩
        Grade examGrade = gradeRepository.findByTakeIdAndGradeType(take.get().getTakeId(), "exam")
                .orElse(new Grade());

        examGrade.setGradeType("exam");
        examGrade.setTakeId(take.get().getTakeId());
        examGrade.setName(course.getTitle());
        examGrade.setGrade(score);
        examGrade.setProportion(examGrade.getProportion());

        gradeRepository.save(examGrade);
    }
}