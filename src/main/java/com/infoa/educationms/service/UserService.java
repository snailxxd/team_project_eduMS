package com.infoa.educationms.service;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.*;

import java.util.List;

public interface UserService {

    // 通用用户接口
    List<UserDTO> searchUser(String keyword, UserRole role);

    UserDTO addUser(UserDTO userDTO);


    // 学生管理
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(int userId);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(int userId, StudentDTO studentDTO);


    // 教师管理
    TeacherDTO getTeacherById(int userId);

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    TeacherDTO updateTeacher(int userId, TeacherDTO teacherDTO);


    // 管理员管理
    AdministratorDTO createAdmin(AdministratorDTO adminDTO);

    // 获取当前用户
    UserDTO getCurrentUser();
}
