package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.PersonalInfoDTO;
import com.infoa.educationms.entities.PersonalInfor;
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
        PersonalInfor info = convertToEntity(dto);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/personal-information/{userId}")
    public ResponseEntity<ApiResult> updatePersonalInfo(
            @PathVariable Integer userId,
            @RequestBody PersonalInfoDTO dto) {
        PersonalInfor info = convertToEntity(dto);
        ApiResult result = educationMSService.updatePersonalInfo(userId, info);
        return ResponseEntity.ok(result);
    }

    private PersonalInfor convertToEntity(PersonalInfoDTO dto) {
        PersonalInfor info = new PersonalInfor();
        info.setName(dto.getName());

        return info;
    }
}