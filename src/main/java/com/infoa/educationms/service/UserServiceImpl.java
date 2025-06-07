package com.infoa.educationms.service;

import com.infoa.educationms.DTO.AdministratorDTO;
import com.infoa.educationms.DTO.StudentDTO;
import com.infoa.educationms.DTO.TeacherDTO;
import com.infoa.educationms.DTO.UserDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Override
    public UserDTO getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User不存在"));
        PersonalInfor pi = personalInfoRepository.findById(user.getPersonalInfoId()).orElse(null);
        return toUserDTO(user, pi);
    }

    // 搜索用户
    @Override
    public List<UserDTO> searchUser(String keyword, UserRole role) {
        List<User> users = (role == null) ? userRepository.findAll() : userRepository.findByUserType(role);
        return users.stream()
                .filter(u -> u.getUserType() != UserRole.ROLE_ADMIN && (keyword == null || keyword.isEmpty() ||
                        u.getAccountNumber().contains(keyword) ||
                        personalInfoRepository.findById(u.getPersonalInfoId())
                                .map(p -> p.getName().contains(keyword))
                                .orElse(false))
                )
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }


    // 新增用户
    @Override
    public UserDTO addUser(UserDTO userDTO) {
        // 先处理 PersonalInfo
        PersonalInfor personalInfor;
        if (userDTO.getPersonalInforId() != null && userDTO.getPersonalInforId() > 0) {
            personalInfor = personalInfoRepository.findById(userDTO.getPersonalInforId())
                    .orElse(new PersonalInfor());
        } else {
            personalInfor = new PersonalInfor();
        }

        personalInfor.setName(userDTO.getName());
        personalInfor.setPhoneNumber(userDTO.getPhoneNumber());
        personalInfor.setPicture(userDTO.getPicture());

        // 保存 PersonalInfo
        personalInfor = personalInfoRepository.save(personalInfor);

        // 创建 User 实体，注意 User 是抽象类，需要根据类型实例化具体子类
        User userEntity;

        String type = userDTO.getType();
        if ("student".equalsIgnoreCase(type)) {
            Student student = new Student();
            student.setDeptName(((StudentDTO) userDTO).getDeptName());
            student.setTotalCredit(((StudentDTO) userDTO).getTotCred() != null ? ((StudentDTO) userDTO).getTotCred() : 0);
            userEntity = student;
        } else if ("teacher".equalsIgnoreCase(type)) {
            Teacher teacher = new Teacher();
            teacher.setDeptName(((TeacherDTO) userDTO).getDeptName());
            teacher.setSalary(((TeacherDTO) userDTO).getSalary() != null ? ((TeacherDTO) userDTO).getSalary() : 0);
            userEntity = teacher;
        } else if ("administrator".equalsIgnoreCase(type)) {
            userEntity = new Admin();
        } else {
            // 默认用UserDTO对应的User实体（一般不会到这）
            throw new IllegalArgumentException("未知用户类型: " + type);
        }

        // 赋值公共字段
        userEntity.setAccountNumber(userDTO.getAccountNumber());
        userEntity.setPassword("default_password");
        userEntity.setPersonalInfoId(personalInfor.getPersonalInfoId());
        userEntity.setUserType(UserRole.valueOf(type.toUpperCase()));

        // 保存 User
        userEntity = userRepository.save(userEntity);

        // 转换成 DTO 返回
        return toUserDTO(userEntity);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("用户不存在"));

        int personalInfoId = user.getPersonalInfoId();

        // 先删除用户
        userRepository.delete(user);

        // 再删除对应的个人信息
        personalInfoRepository.deleteById(personalInfoId);
    }


    // 学生管理
    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(s -> {
                    PersonalInfor pi = personalInfoRepository.findById(s.getPersonalInfoId()).orElse(null);
                    return toStudentDTO(s, pi);
                })
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(int userId) {
        Student s = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student不存在"));
        PersonalInfor pi = personalInfoRepository.findById(s.getPersonalInfoId()).orElse(null);
        return toStudentDTO(s, pi);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        PersonalInfor pi = new PersonalInfor();
        pi.setName(studentDTO.getName());
        pi.setPhoneNumber(studentDTO.getPhoneNumber());
        pi.setPicture(studentDTO.getPicture());
        pi = personalInfoRepository.save(pi);

        Student student = new Student();
        student.setAccountNumber(studentDTO.getAccountNumber());
        student.setPassword("123456");
        student.setPersonalInfoId(pi.getPersonalInfoId());
        student.setUserType(UserRole.ROLE_STUDENT);
        student.setDeptName(studentDTO.getDeptName());
        student.setTotalCredit(studentDTO.getTotCred() == null ? 0 : studentDTO.getTotCred());
        student = studentRepository.save(student);

        return toStudentDTO(student, pi);
    }

    @Override
    public StudentDTO updateStudent(int userId, StudentDTO studentDTO) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student不存在"));
        PersonalInfor pi = personalInfoRepository.findById(student.getPersonalInfoId())
                .orElseThrow(() -> new RuntimeException("PersonalInfo不存在"));

        // 更新个人信息
        pi.setName(studentDTO.getName());
        pi.setPhoneNumber(studentDTO.getPhoneNumber());
        pi.setPicture(studentDTO.getPicture());
        personalInfoRepository.save(pi);

        // 更新学生特有字段
        student.setDeptName(studentDTO.getDeptName());
        student.setTotalCredit(studentDTO.getTotCred() == null ? 0 : studentDTO.getTotCred());
        studentRepository.save(student);

        return toStudentDTO(student, pi);
    }

    // 教师管理
    @Override
    public TeacherDTO getTeacherById(int userId) {
        Teacher teacher = teacherRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Teacher不存在"));
        PersonalInfor pi = personalInfoRepository.findById(teacher.getPersonalInfoId()).orElse(null);
        return toTeacherDTO(teacher, pi);
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        PersonalInfor pi = new PersonalInfor();
        pi.setName(teacherDTO.getName());
        pi.setPhoneNumber(teacherDTO.getPhoneNumber());
        pi.setPicture(teacherDTO.getPicture());
        pi = personalInfoRepository.save(pi);

        Teacher teacher = new Teacher();
        teacher.setAccountNumber(teacherDTO.getAccountNumber());
        teacher.setPassword("默认密码");
        teacher.setPersonalInfoId(pi.getPersonalInfoId());
        teacher.setUserType(UserRole.ROLE_TEACHER);
        teacher.setDeptName(teacherDTO.getDeptName());
        teacher.setSalary(teacherDTO.getSalary() == null ? 0 : teacherDTO.getSalary());
        teacher = teacherRepository.save(teacher);

        return toTeacherDTO(teacher, pi);
    }

    @Override
    public TeacherDTO updateTeacher(int userId, TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Teacher不存在"));
        PersonalInfor pi = personalInfoRepository.findById(teacher.getPersonalInfoId())
                .orElseThrow(() -> new RuntimeException("PersonalInfo不存在"));

        pi.setName(teacherDTO.getName());
        pi.setPhoneNumber(teacherDTO.getPhoneNumber());
        pi.setPicture(teacherDTO.getPicture());
        personalInfoRepository.save(pi);

        teacher.setDeptName(teacherDTO.getDeptName());
        teacher.setSalary(teacherDTO.getSalary() == null ? 0 : teacherDTO.getSalary());
        teacherRepository.save(teacher);

        return toTeacherDTO(teacher, pi);
    }

    // 管理员管理
    @Override
    public AdministratorDTO createAdmin(AdministratorDTO adminDTO) {
        PersonalInfor pi = new PersonalInfor();
        pi.setName(adminDTO.getName());
        pi.setPhoneNumber(adminDTO.getPhoneNumber());
        pi.setPicture(adminDTO.getPicture());
        pi = personalInfoRepository.save(pi);

        Admin admin = new Admin();
        admin.setAccountNumber(adminDTO.getAccountNumber());
        admin.setPassword("默认密码");
        admin.setPersonalInfoId(pi.getPersonalInfoId());
        admin.setUserType(UserRole.ROLE_ADMIN);
        admin = adminRepository.save(admin);

        return toAdminDTO(admin, pi);
    }

    // 辅助方法：实体转DTO
    private UserDTO toUserDTO(User user) {
        PersonalInfor pi = personalInfoRepository.findById(user.getPersonalInfoId()).orElse(null);
        return toUserDTO(user, pi);
    }

    private UserDTO toUserDTO(User user, PersonalInfor pi) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setAccountNumber(user.getAccountNumber());
        dto.setPersonalInforId(user.getPersonalInfoId());
        dto.setType(user.getUserType().name());
        if (pi != null) {
            dto.setName(pi.getName());
            dto.setPhoneNumber(pi.getPhoneNumber());
            dto.setPicture(pi.getPicture());
        }
        return dto;
    }

    private StudentDTO toStudentDTO(Student student, PersonalInfor pi) {
        StudentDTO dto = new StudentDTO();
        dto.setUserId(student.getUserId());
        dto.setAccountNumber(student.getAccountNumber());
        dto.setPersonalInforId(student.getPersonalInfoId());
        dto.setType(student.getUserType().name().toLowerCase());
        dto.setDeptName(student.getDeptName());
        dto.setTotCred(student.getTotalCredit());
        if (pi != null) {
            dto.setName(pi.getName());
            dto.setPhoneNumber(pi.getPhoneNumber());
            dto.setPicture(pi.getPicture());
        }
        return dto;
    }

    private TeacherDTO toTeacherDTO(Teacher teacher, PersonalInfor pi) {
        TeacherDTO dto = new TeacherDTO();
        dto.setUserId(teacher.getUserId());
        dto.setAccountNumber(teacher.getAccountNumber());
        dto.setPersonalInforId(teacher.getPersonalInfoId());
        dto.setType(teacher.getUserType().name().toLowerCase());
        dto.setDeptName(teacher.getDeptName());
        dto.setSalary(teacher.getSalary());
        if (pi != null) {
            dto.setName(pi.getName());
            dto.setPhoneNumber(pi.getPhoneNumber());
            dto.setPicture(pi.getPicture());
        }
        return dto;
    }

    private AdministratorDTO toAdminDTO(Admin admin, PersonalInfor pi) {
        AdministratorDTO dto = new AdministratorDTO();
        dto.setUserId(admin.getUserId());
        dto.setAccountNumber(admin.getAccountNumber());
        dto.setPersonalInforId(admin.getPersonalInfoId());
        dto.setType(admin.getUserType().name().toLowerCase());
        if (pi != null) {
            dto.setName(pi.getName());
            dto.setPhoneNumber(pi.getPhoneNumber());
            dto.setPicture(pi.getPicture());
        }
        return dto;
    }
}
