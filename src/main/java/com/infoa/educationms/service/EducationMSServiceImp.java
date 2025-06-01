package com.infoa.educationms.service;

import com.infoa.educationms.entities.*;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.queries.GradeDetail;
import com.infoa.educationms.queries.UserList;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class EducationMSServiceImp implements EducationMSService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeChangeRepository gradeChangeRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    //-------------------------- 通用模块接口实现 -------------------------------//
    @Override
    public ApiResult getPersonalInfo(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ApiResult(false, "用户不存在");
        }
        PersonalInfor info = personalInfoRepository.findById(user.getPersonalInfoId()).orElse(null);
        return new ApiResult(true, "查询成功", info);
    }

    @Override
    public ApiResult updatePersonalInfo(int userId, PersonalInfor info) {
        User currentUser = getCurrentUser();
        // 管理员或用户本人可修改
        if (!currentUser.getUserType().equals(UserRole.ROLE_ADMIN)) {
            if (currentUser.getUserId() != userId) {
                return new ApiResult(false, "无权修改他人信息");
            }
        }
        PersonalInfor existingInfo = personalInfoRepository.findById(info.getPersonalInfoId()).orElse(null);
        if (existingInfo == null) {
            return new ApiResult(false, "个人信息不存在");
        }
        existingInfo.setName(info.getName());
        existingInfo.setPhoneNumber(info.getPhoneNumber());
        personalInfoRepository.save(existingInfo);
        return new ApiResult(true, "个人信息更新成功");
    }

    @Override
    public ApiResult querySection() {
        List<Section> sections = sectionRepository.findAll();
        return new ApiResult(true, "查询成功", sections);
    }

    //------------------------ 学生模块接口实现 ------------------------//
    @Override
    public ApiResult queryGrade() {
        int studentId = getCurrentUserId();
        if (!isCurrentUser(studentId)) {
            return new ApiResult(false, "无权查看他人成绩");
        }
        List<Take> takes = takeRepository.findByStudentId(studentId);
        List<Grade> grades = takes.stream()
            .flatMap(t -> gradeRepository.findByTakeId(t.getTakeId()).stream())
            .collect(Collectors.toList());
        if (grades.isEmpty()) {
            return new ApiResult(false, "未找到成绩记录");
        }
        return new ApiResult(true, "成绩查询成功", new GradeDetail(grades));
    }

    @Override
    public ApiResult analyzeGradeStu() {
        int studentId = getCurrentUserId();
        List<Take> takes = takeRepository.findByStudentId(studentId);

        List<GradeDetail> gradeDetails = new ArrayList<>();
        for (Take take : takes) {
            List<Grade> grades = gradeRepository.findByTakeId(take.getTakeId());
            if (!grades.isEmpty()) {
                gradeDetails.add(new GradeDetail(new ArrayList<>(grades)));
            }
        }

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("studentId", studentId);
        analysis.put("gradeDetails", gradeDetails);

        return new ApiResult(true, "学生成绩分析成功", analysis);
    }

    //----------------------- 管理员模块接口实现 ----------------------------//
    @Override
    public ApiResult addUser(User user) {
        if (!isAdmin()) {
            return new ApiResult(false, "权限不足：仅管理员可添加用户");
        }
        if (userRepository.existsByAccountNumber(user.getAccountNumber())) {
            return new ApiResult(false, "账号已存在");
        }
        userRepository.save(user);
        return new ApiResult(true, "用户添加成功");
    }

    @Override
    public ApiResult deleteUser() {
        int userId = getCurrentUserId();
        if (!isAdmin()) {
            return new ApiResult(false, "权限不足：仅管理员可删除用户");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ApiResult(false, "用户不存在");
        }
        // 检查关联数据
        List<Take> takes = takeRepository.findByStudentId(userId);
        if (!takes.isEmpty()) {
            return new ApiResult(false, "存在关联选课记录，无法删除");
        }
        userRepository.delete(user);
        return new ApiResult(true, "用户删除成功");
    }

    @Override
    public ApiResult searchUser(String keyword, UserRole role) {
        if (!isAdmin()) {
        return new ApiResult(false, "权限不足：仅管理员可搜索用户");
        }
    
        List<User> users;
        
        // 如果没有提供搜索条件，则返回所有用户
        if ((keyword == null || keyword.isEmpty()) && role == null) {
            users = userRepository.findAll();
        } 
        // 如果只提供了关键字
        else if (role == null) {
            users = userRepository.findByAccountNumberContaining(keyword);
        }
        // 如果只提供了角色
        else if (keyword == null || keyword.isEmpty()) {
            users = userRepository.findByUserType(role);
        }
        // 如果同时提供了关键字和角色
        else {
            users = userRepository.findByAccountNumberContainingAndUserType(keyword, role);
        }
        
        return new ApiResult(true, "查询成功", new UserList(users));
    }


    @Override
    public ApiResult updateUser() {
        User user = getCurrentUser();
        if (!isAdmin()) {
            return new ApiResult(false, "权限不足：仅管理员可修改用户");
        }
        User existingUser = userRepository.findById(user.getUserId()).orElse(null);
        if (existingUser == null) {
            return new ApiResult(false, "用户不存在");
        }
        existingUser.setAccountNumber(user.getAccountNumber());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        return new ApiResult(true, "用户信息更新成功");
    }

    @Override
    public ApiResult auditGradeChange(int changeId, boolean isApproved) {
        if (!isAdmin()) {
            return new ApiResult(false, "权限不足：仅管理员可审核");
        }
        GradeChange change = gradeChangeRepository.findById(changeId).orElse(null);
        if (change == null) {
            return new ApiResult(false, "申请记录不存在");
        }
        if (isApproved) {
            List<Grade> grades = gradeRepository.findByTakeId(changeId);
            Grade grade = grades.get(changeId);
            if (grade == null) {
                return new ApiResult(false, "关联成绩不存在");
            }
            grade.setGrade(change.getNewGrade());
            gradeRepository.save(grade);
        }
        change.setResult(isApproved);
        change.setCheckTime(LocalDateTime.now());
        gradeChangeRepository.save(change);
        return new ApiResult(true, "审核操作完成");
    }

    @Override
    public ApiResult getAllTeachersAndStudents() {
        if (!isAdmin()) {
            return new ApiResult(false, "权限不足：仅管理员可执行此操作");
        }
        
        // 获取所有老师和学生
        List<User> teachers = userRepository.findByUserType(UserRole.ROLE_TEACHER);
        List<User> students = userRepository.findByUserType(UserRole.ROLE_STUDENT);
        
        // 合并结果
        List<User> allTeachersAndStudents = new ArrayList<>();
        allTeachersAndStudents.addAll(teachers);
        allTeachersAndStudents.addAll(students);
        
        return new ApiResult(true, "获取全体师生成功", new UserList(allTeachersAndStudents));
    }

    @Override
    public ApiResult queryDepartment() {
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            return new ApiResult(false, "未找到部门信息");
        }
        return new ApiResult(true, "查询成功", departments);
    }

    //---------------------------- 教师模块接口实现 -----------------------------//
    @Override
    public ApiResult addSection(Section section) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可添加开课信息");
        }
        section.setTeacherId(getCurrentUserId());
        sectionRepository.save(section);
        return new ApiResult(true, "开课信息添加成功");
    }

    @Override
    public ApiResult deleteSection(int sectionId) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可删除开课信息");
        }
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (section == null) {
            return new ApiResult(false, "开课信息不存在");
        }
        // 检查是否为本教师开设的课程
        if (section.getTeacherId() != getCurrentUserId()) {
            return new ApiResult(false, "无权删除他人开课信息");
        }
        // 检查关联选课记录
        List<Take> takes = takeRepository.findBySectionId(sectionId);
        if (!takes.isEmpty()) {
            return new ApiResult(false, "存在关联选课记录，无法删除");
        }
        sectionRepository.delete(section);
        return new ApiResult(true, "开课信息删除成功");
    }

    @Override
    public ApiResult updateSection(Section section) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可修改开课信息");
        }
        Section existingSection = sectionRepository.findById(section.getSectionId()).orElse(null);
        if (existingSection == null) {
            return new ApiResult(false, "开课信息不存在");
        }
        // 验证教师权限
        if (existingSection.getTeacherId() != getCurrentUserId()) {
            return new ApiResult(false, "无权修改他人开课信息");
        }
        existingSection.setSemester(section.getSemester());
        existingSection.setYear(section.getYear());
        existingSection.setClassroomId(section.getClassroomId());
        sectionRepository.save(existingSection);
        return new ApiResult(true, "开课信息更新成功");
    }

    @Override
    public ApiResult submitGrade(List<Grade> grades) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可提交成绩");
        }
        for (Grade grade : grades) {
            // 验证教师是否有权限操作该课程
            Take take = takeRepository.findById(grade.getTakeId()).orElse(null);
            if (take == null) {
                return new ApiResult(false, "选课记录不存在");
            }
            Section section = sectionRepository.findById(take.getSectionId()).orElse(null);
            if (section == null || section.getTeacherId() != getCurrentUserId()) {
                return new ApiResult(false, "无权提交该课程成绩");
            }
        }
        gradeRepository.saveAll(grades);
        return new ApiResult(true, "成绩提交成功");
    }

    @Override
    public ApiResult changeGrade(int takeId, int newGrade, String reason) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可申请修改成绩");
        }
        List<Grade> grades = gradeRepository.findByTakeId(takeId);
        Grade grade = grades.get(takeId);
        if (grade == null) {
            return new ApiResult(false, "关联成绩不存在");
        }
        // 创建修改申请
        GradeChange change = new GradeChange();
        change.setTakeId(takeId);
        change.setTeacherId(getCurrentUserId());
        change.setNewGrade(newGrade);
        change.setApplyTime(LocalDateTime.now());
        change.setResult(false);
        grade.setGrade(newGrade);
        gradeChangeRepository.save(change);
        return new ApiResult(true, "成绩修改申请已提交");
    }

    @Override
    public ApiResult analyzeGradeTeacher(int sectionId) {
        if (!isTeacher()) {
            return new ApiResult(false, "权限不足：仅教师可分析成绩");
        }
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (section == null || section.getTeacherId() != getCurrentUserId()) {
            return new ApiResult(false, "无权查看该课程成绩");
        }
        List<Grade> grades = gradeRepository.findBySectionId(sectionId);
        if (grades.isEmpty()) {
            return new ApiResult(false, "未找到成绩记录");
        }
        double average = grades.stream().mapToInt(Grade::getGrade).average().orElse(0.0);
        Map<String, Long> distribution = grades.stream()
                .collect(Collectors.groupingBy(Grade::getGradeType, Collectors.counting()));
        Map<String, Object> result = new HashMap<>();
        result.put("average", average);
        result.put("distribution", distribution);
        return new ApiResult(true, "成绩分析成功", result);
    }

    //-------------------------------- 辅助方法 --------------------------------//
    private boolean isAdmin() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUserType() == UserRole.ROLE_ADMIN;
    }

    private boolean isTeacher() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUserType() == UserRole.ROLE_TEACHER;
    }

    private boolean isCurrentUser(int userId) {
        return getCurrentUserId() == userId;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName(); // 当前登录用户名

        return userRepository.findByAccountNumber(account)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在，账号：" + account));
    }

    private int getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
    
}