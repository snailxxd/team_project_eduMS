package com.infoa.educationms.service;

import com.infoa.educationms.DTO.CaClassroomDTO;
import com.infoa.educationms.DTO.CaClassroomUpdateDTO;
import com.infoa.educationms.DTO.CaCourseDTO;
import com.infoa.educationms.DTO.CaTeacherDTO;
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
    public List<CaCourseDTO> getAllCourses() {
        // 1. 获取所有课程
        List<Course> courses = courseRepository.findAll();

        // 2. 转换为DTO
        return courses.parallelStream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CaCourseDTO convertToDTO(Course course){
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
        return new CaCourseDTO(
                course.getCourseId(),
                course.getTitle(),
                course.getCredits(),
                teacherName,
                classroomStr
        );
    }

    @Override
    public List<CaClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::convertToClassroomDTO)
                .collect(Collectors.toList());
    }

    private CaClassroomDTO convertToClassroomDTO(Classroom classroom) {
        return new CaClassroomDTO(
                classroom.getClassroomId(),
                classroom.getCapacity(),
                classroom.getBuilding()
        );
    }

    @Override
    public List<CaTeacherDTO> getAllTeachers() {
        // 1. 获取所有教师
        List<Teacher> teachers = teacherRepository.findAll();

        // 2. 批量获取关联的PersonalInfo
        Map<Integer, PersonalInfor> personalInfoMap = personalInfoRepository.findAllById(
                        teachers.stream()
                                .map(Teacher::getPersonalInfoId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                ).stream()
                .collect(Collectors.toMap(PersonalInfor::getPersonalInfoId, p -> p));

        // 3. 转换为DTO
        return teachers.stream()
                .map(teacher -> convertToTeacherDTO(teacher, personalInfoMap))
                .collect(Collectors.toList());
    }

    private CaTeacherDTO convertToTeacherDTO(Teacher teacher, Map<Integer, PersonalInfor> personalInfoMap) {
        // 获取PersonalInfo
        PersonalInfor personalInfor = teacher.getPersonalInfoId() != 0 ?
                personalInfoMap.get(teacher.getPersonalInfoId()) : null;

        return new CaTeacherDTO(
                teacher.getUserId(),
                personalInfor != null ? personalInfor.getName() : "未知教师", // 从PersonalInfo获取姓名
                teacher.getDeptName()
        );
    }

    @Override
    public void addClassroom(CaClassroomDTO caClassroomDTO) {
        // 将DTO转换为实体
        Classroom classroom = convertToEntity(caClassroomDTO);
        classroomRepository.save(classroom);
    }

    private Classroom convertToEntity(CaClassroomDTO caClassroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setClassroomId(caClassroomDTO.getClassroomId());
        classroom.setCapacity(caClassroomDTO.getCapacity());
        classroom.setBuilding(caClassroomDTO.getBuilding());
        return classroom;
    }

    @Override
    public void updateClassroom(Integer classroomId, CaClassroomUpdateDTO classroom) {
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