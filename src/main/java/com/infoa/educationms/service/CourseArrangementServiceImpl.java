package com.infoa.educationms.service;

import com.infoa.educationms.entities.Classroom;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.Teacher;
import com.infoa.educationms.repository.ClassroomRepository;
import com.infoa.educationms.repository.CourseRepository;
import com.infoa.educationms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseArrangementServiceImpl implements CourseArrangementService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public void addClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    @Override
    public void updateClassroom(Integer classroomId, Classroom classroom) {
        Classroom existing = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found with id: " + classroomId));

        // Update fields
        existing.setCampus(classroom.getCampus());
        existing.setCapacity(classroom.getCapacity());
        existing.setRoomNumber(classroom.getRoomNumber());
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