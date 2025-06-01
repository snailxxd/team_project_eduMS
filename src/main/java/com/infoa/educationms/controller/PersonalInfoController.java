package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.PersonalInfoDTO;
import com.infoa.educationms.repository.PersonalInfoRepository;
import com.infoa.educationms.service.PersonalInforServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonalInfoController {

    @Autowired
    private PersonalInforServer personalInforServer;

    @PostMapping("/personal-information")
    public ResponseEntity<PersonalInfoDTO> createPersonalInfo(@RequestBody PersonalInfoDTO personalInfoDTO) {
        // 实际上服务层没有创建，只能用 updatePersonalInfo 来设置或更新
        PersonalInfoDTO personalInfoDTO1 = personalInforServer.createPersonalInfor(personalInfoDTO);
        return ResponseEntity.ok(personalInfoDTO1);
    }

    @PutMapping("/personal-information/{userId}")
    public ResponseEntity<PersonalInfoDTO> updatePersonalInfo(
            @PathVariable Integer userId,
            @RequestBody PersonalInfoDTO dto) {
        PersonalInfoDTO personalInfoDTO = personalInforServer.updatePersonalInfor(dto, userId);
        return ResponseEntity.ok(personalInfoDTO);
    }

}