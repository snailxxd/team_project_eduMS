package com.infoa.educationms.service;

import com.infoa.educationms.DTO.GradeDTO;
import com.infoa.educationms.DTO.GradeStatusDTO;
import com.infoa.educationms.DTO.PersonalInfoDTO;
import com.infoa.educationms.DTO.StudentGradeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonalInforServer {

    PersonalInfoDTO createPersonalInfor(PersonalInfoDTO personalInfoDTO);

    PersonalInfoDTO updatePersonalInfor(PersonalInfoDTO personalInfoDTO, Integer personalInforId);
}