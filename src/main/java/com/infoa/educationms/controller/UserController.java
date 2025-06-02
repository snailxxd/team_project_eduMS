package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.*;

import com.infoa.educationms.service.JwtTokenProvider;
import com.infoa.educationms.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    // 获取当前登录用户信息
    @GetMapping("/user/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        return ResponseEntity.ok(userDTO);
    }

    // 获取所有用户（此处调用 searchUser）
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = userService.searchUser("", null);
        return ResponseEntity.ok(result);
    }

    // 创建用户（仅限通用用户）
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO result = userService.addUser(userDTO);
        return ResponseEntity.ok(result);
    }

    // 获取所有学生
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> result = userService.getAllStudents();
        return ResponseEntity.ok(result);
    }

    // 获取某个学生信息
    @GetMapping("/students/{userId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int userId) {
        StudentDTO result = userService.getStudentById(userId);
        return ResponseEntity.ok(result);
    }

    // 创建学生
    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO result = userService.createStudent(studentDTO);
        return ResponseEntity.ok(result);
    }

    // 更新学生
    @PutMapping("/students/{userId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable int userId, @RequestBody StudentDTO studentDTO) {
        StudentDTO result = userService.updateStudent(userId, studentDTO);
        return ResponseEntity.ok(result);
    }

    // 获取教师信息
    @GetMapping("/teachers/{userId}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable int userId) {
        TeacherDTO result = userService.getTeacherById(userId);
        return ResponseEntity.ok(result);
    }

    // 创建教师
    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO result = userService.createTeacher(teacherDTO);
        return ResponseEntity.ok(result);
    }

    // 更新教师
    @PutMapping("/teachers/{userId}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable int userId, @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO result = userService.updateTeacher(userId, teacherDTO);
        return ResponseEntity.ok(result);
    }

    // 创建管理员
    @PostMapping("/administrators")
    public ResponseEntity<AdministratorDTO> createAdmin(@RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO result = userService.createAdmin(adminDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/get_userid")
    public ResponseEntity<?> getUserIdFromToken(HttpServletRequest request) {
        // 1. 从请求头中获取 Authorization 头部
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. 校验 Authorization 头部是否存在以及是否以 "Bearer " 开头
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid");
        }

        // 3. 提取 Token 字符串
        String token = authHeader.substring(7); // "Bearer " 后面的部分就是 token

        try {
            Integer userId = jwtTokenProvider.getClaimFromToken(token, claims -> claims.get("userId", Integer.class));

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID not found in token");
            }

            // 6. 成功获取 userId，可以返回 userId 或根据 userId 查询更多用户信息
            return ResponseEntity.ok(userId);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token format is unsupported");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is malformed");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token signature validation failed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token claims: " + e.getMessage());
        } catch (Exception e) {
            // 其他未知错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing token: " + e.getMessage());
        }
    }

    @GetMapping("user/get_type")
    public ResponseEntity<?> getUserTypeFromToken(HttpServletRequest request) {
        // 1. 从请求头中获取 Authorization 头部
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. 校验 Authorization 头部是否存在以及是否以 "Bearer " 开头
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid");
        }

        // 3. 提取 Token 字符串
        String token = authHeader.substring(7); // "Bearer " 后面的部分就是 token

        try {
            String type = jwtTokenProvider.getClaimFromToken(token, claims -> claims.get("type", String.class));

            if (type == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID not found in token");
            }

            // 6. 成功获取 type，可以返回 type 或根据 type 查询更多用户信息
            return ResponseEntity.ok(type);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token format is unsupported");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is malformed");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token signature validation failed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token claims: " + e.getMessage());
        } catch (Exception e) {
            // 其他未知错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing token: " + e.getMessage());
        }
    }
}


