package com.infoa.educationms.service;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OnlineQuizServiceImpl implements OnlineQuizService {

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

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public List<OqCourseForTeacherDTO> getCoursesByTeacher(Integer teacherId) {
        // 验证教师存在
        teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher is not found: " + teacherId));

        List<Course> courses = courseRepository.findByTeacherId(teacherId);

        return courses.stream()
                .map(c -> new OqCourseForTeacherDTO(
                        c.getCourseId(),
                        c.getTitle(),
                        c.getCredits()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<OqStudentDTO> getStudentsByCourse(Integer courseId) {
        // 1. 验证课程存在
        courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course is not found: " + courseId));

        // 2. 获取课程的所有section ID
        List<Integer> sectionIds = sectionRepository.findByCourseId(courseId).stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());

        // 3. 获取选课学生ID
        Set<Integer> studentIds = takeRepository.findBySectionIdIn(sectionIds).stream()
                .map(Take::getStudentId)
                .collect(Collectors.toSet());

        List<Student> students = studentRepository.findAllById(studentIds);

        // 4. 批量获取关联的PersonalInfo
        Map<Integer, PersonalInfor> personalInfoMap = personalInfoRepository.findAllById(
                        students.stream()
                                .map(Student::getPersonalInfoId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                ).stream()
                .collect(Collectors.toMap(PersonalInfor::getPersonalInfoId, p -> p));

        // 5. 转换为DTO
        return students.stream()
                .map(student -> convertToOqStudentDTO(student, personalInfoMap))
                .collect(Collectors.toList());
    }

    private OqStudentDTO convertToOqStudentDTO(Student student, Map<Integer, PersonalInfor> personalInfoMap) {
        // 获取PersonalInfo
        PersonalInfor personalInfor = student.getPersonalInfoId() != 0 ?
                personalInfoMap.get(student.getPersonalInfoId()) : null;

        return new OqStudentDTO(
                student.getUserId(),
                personalInfor != null ? personalInfor.getName() : "未知学生", // 从PersonalInfo获取姓名
                student.getDeptName()
        );
    }


    @Override
    public List<OqCourseForStudentDTO> getCoursesByStudent(Integer studentId) {
        List<Take> takes;
        // 1. 查询学生的所有选课记录
        if(studentId == -1)
        {
            takes = takeRepository.findAll();
        }
        else {
            takes = takeRepository.findByStudentId(studentId);
            if (takes.isEmpty()) {
                return Collections.emptyList();
            }
        }

        // 2. 获取sectionId列表
        List<Integer> sectionIds = takes.stream()
                .map(Take::getSectionId)
                .collect(Collectors.toList());

        // 3. 查询这些section对应的课程ID和教师信息
        Map<Integer, Integer> courseTeacherMap = sectionRepository.findAllById(sectionIds).stream()
                .collect(Collectors.toMap(
                        Section::getCourseId,
                        Section::getTeacherId,
                        (existing, replacement) -> existing // 如果有重复的courseId，保留现有的
                ));

        // 4. 查询课程详细信息并转换为DTO
        return courseRepository.findAllById(courseTeacherMap.keySet()).stream()
                .map(course -> {
                    // 获取教师信息
                    Integer teacherId = courseTeacherMap.get(course.getCourseId());
                    String teacherName = "";
                    // 可以进一步查询教师详细信息，例如姓名
                     Optional<Teacher> teacher = teacherRepository.findById(teacherId);
                     if (teacher.isPresent()) {
                         Optional<PersonalInfor> personalInfo = personalInfoRepository.findById(teacher.get().getPersonalInfoId());
                         if (personalInfo.isPresent()) {
                             teacherName = personalInfo.get().getName();
                         }
                     }

                    return new OqCourseForStudentDTO(
                            course.getCourseId(),
                            sectionRepository.findFirstByCourseId(course.getCourseId()).get().getSectionId(),
                            course.getTitle(),
                            course.getCredits(),
                            teacherName
                    );
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

    @Override
    public List<Integer> getSectionIdsByCourse(Integer courseId) {
        return sectionRepository.findByCourseId(courseId)
                .stream()
                .map(Section::getSectionId)
                .collect(Collectors.toList());
    }

    @Override
    public List<OqTimeSlotDTO> getTimeSlotInfoBySection(Integer sectionId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section not found with id: " + sectionId));

        // 2. Get the time slot IDs from the section
        List<Integer> timeSlotIds = section.getTimeSlotIdList();

        // 3. Get all time slots by their IDs
        List<TimeSlot> timeSlots = timeSlotRepository.findAllById(timeSlotIds);

        // 4. Convert to DTOs
        return timeSlots.stream()
                .map(ts -> new OqTimeSlotDTO(
                        ts.getDay(),
                        ts.getStartTime(),
                        ts.getEndTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OqTeacherCourseInfoDTO> getTeacherCourseDetails(Integer teacherId){
        List<Section> sections = sectionRepository.findByTeacherId(teacherId);

        return sections.stream().map(section -> {
            Course course = courseRepository.findById(section.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("课程不存在: " + section.getCourseId()));

            List<Integer> timeSlotIds = section.getTimeSlotIdList();
            List<TimeSlot> timeSlots = timeSlotRepository.findAllById(timeSlotIds);

            if (timeSlots.isEmpty()) {
                throw new IllegalArgumentException("找不到对应的时间段");
            }

            TimeSlot firstTimeSlot = timeSlots.get(0); // 假设只有一个时间段

            return new OqTeacherCourseInfoDTO(
                    section.getSectionId(),
                    course.getTitle(),
                    section.getSemester(),
                    section.getYear(),
                    convertDayToChinese(firstTimeSlot.getDay()),
                    formatTime(firstTimeSlot.getStartTime()),
                    formatTime(firstTimeSlot.getEndTime())
            );
        }).collect(Collectors.toList());
    }

    private String convertDayToChinese(int day) {
        switch (day) {
            case 1: return "周一";
            case 2: return "周二";
            case 3: return "周三";
            case 4: return "周四";
            case 5: return "周五";
            case 6: return "周六";
            case 7: return "周日";
            default: return "未知";
        }
    }

    private String formatTime(LocalTime time) {
        return time != null ? time.toString() : "";
    }
}