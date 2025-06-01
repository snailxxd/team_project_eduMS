package com.infoa.educationms.service;

import com.infoa.educationms.DTO.ClassroomDTO;
import com.infoa.educationms.DTO.ClassroomUpdateDTO;
import com.infoa.educationms.DTO.CourseDTO;
import com.infoa.educationms.DTO.TeacherDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseArrangementServiceImpl implements CourseArrangementService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Override
    public List<CourseDTO> getAllCourses() {
        // 1. 获取所有课程
        List<Course> courses = courseRepository.findAll();

        // 2. 转换为DTO
        return courses.parallelStream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CourseDTO convertToDTO(Course course){
        // 3. 获取课程的第一个section
        Optional<Section> firstSection = sectionRepository.findFirstByCourseId(course.getCourseId());
        // 4. 初始化教师和教室信息
        String teacherName = null;
        String classroomStr = null;
        // 5. 如果存在section，获取关联信息
        if (firstSection.isPresent()) {
            Section section = firstSection.get();
            // 获取教师信息
            teacherName = teacherRepository.findById(section.getTeacherId())
                    .map(Teacher::getAccountNumber)
                    .orElse(null);
            // 获取教室信息
            classroomStr = classroomRepository.findById(section.getClassroomId())
                    .map(c -> c.getBuilding() + "-" + c.getRoomNumber())
                    .orElse(null);
        }
        // 6. 构建DTO
        return new CourseDTO(
                String.valueOf(course.getCourseId()),
                course.getTitle(),
                course.getCredits(),
                teacherName,
                classroomStr
        );
    }

    @Override
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::convertToClassroomDTO)
                .collect(Collectors.toList());
    }

    private ClassroomDTO convertToClassroomDTO(Classroom classroom) {
        return new ClassroomDTO(
                classroom.getClassroomId(),
                classroom.getCapacity(),
                classroom.getBuilding()
        );
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        // 1. 获取所有教师
        List<Teacher> teachers = teacherRepository.findAll();

        // 2. 批量获取关联的PersonalInfo
        Map<Integer, PersonalInfo> personalInfoMap = personalInfoRepository.findAllById(
                        teachers.stream()
                                .map(Teacher::getPersonalInfoId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                ).stream()
                .collect(Collectors.toMap(PersonalInfo::getPersonalInfoId, p -> p));

        // 3. 转换为DTO
        return teachers.stream()
                .map(teacher -> convertToTeacherDTO(teacher, personalInfoMap))
                .collect(Collectors.toList());
    }

    private TeacherDTO convertToTeacherDTO(Teacher teacher, Map<Integer, PersonalInfo> personalInfoMap) {
        // 获取PersonalInfo
        PersonalInfo personalInfo = teacher.getPersonalInfoId() != 0 ?
                personalInfoMap.get(teacher.getPersonalInfoId()) : null;

        return new TeacherDTO(
                teacher.getUserId(),
                personalInfo != null ? personalInfo.getName() : "未知教师", // 从PersonalInfo获取姓名
                teacher.getDeptName()
        );
    }

    @Override
    public void addClassroom(ClassroomDTO classroomDTO) {
        // 将DTO转换为实体
        Classroom classroom = convertToEntity(classroomDTO);
        classroomRepository.save(classroom);
    }

    private Classroom convertToEntity(ClassroomDTO classroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setClassroomId(classroomDTO.getClassroomId());
        classroom.setCapacity(classroomDTO.getCapacity());
        classroom.setBuilding(classroomDTO.getBuilding());
        return classroom;
    }

    @Override
    public void updateClassroom(Integer classroomId, ClassroomUpdateDTO classroom) {
        Classroom existing = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found with id: " + classroomId));

        existing.setCapacity(classroom.getCapacity());
        existing.setBuilding(classroom.getBuilding());

        classroomRepository.save(existing);
    }

    @Override
    public void deleteClassroom(Integer classroomId) {
        classroomRepository.deleteById(classroomId);
    }

    @Override
    public Classroom getClassroomById(Integer classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found with id: " + classroomId));
    }
}