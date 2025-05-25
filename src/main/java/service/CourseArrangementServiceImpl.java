package service;

import entities.Classroom;
import entities.Course;
import entities.Teacher;
import repository.ClassroomRepository;
import repository.CourseRepository;
import repository.TeacherRepository;

import java.util.List;

public class CourseArrangementServiceImpl implements CourseArrangementService {

    private CourseRepository courseRepository;
    private ClassroomRepository classroomRepository;
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