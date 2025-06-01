package com.infoa.educationms.controller;

import com.infoa.educationms.entities.PersonalInfo;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonalInfoController {

    @Autowired
    private EducationMSService educationMSService;

    @PostMapping("/personal-information")
    public ResponseEntity<ApiResult> createPersonalInfo(@RequestParam int userId, @RequestBody PersonalInfoDTO dto) {
        // 实际上服务层没有创建，只能用 updatePersonalInfo 来设置或更新
        PersonalInfo info = convertToEntity(dto);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/personal-information/{userId}")
    public ResponseEntity<ApiResult> updatePersonalInfo(
            @PathVariable Integer userId,
            @RequestBody PersonalInfoDTO dto) {
        PersonalInfo info = convertToEntity(dto);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    private PersonalInfo convertToEntity(PersonalInfoDTO dto) {
        PersonalInfo info = new PersonalInfo();
        info.setName(dto.getName());
        info.setEmail(dto.getEmail());
        // 设置更多字段...
        return info;
    }
}