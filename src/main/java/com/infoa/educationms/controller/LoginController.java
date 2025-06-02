package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        List<LoginResponseDTO> loginResponseDTO = loginService.checkLogin(loginRequest);
        if (loginResponseDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(java.util.Map.of("message", "账号或密码错误"));
        }
        else {
            return ResponseEntity.ok(loginResponseDTO.get(0));
        }

    }


}