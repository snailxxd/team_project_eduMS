package com.infoa.educationms.service;

import com.infoa.educationms.DTO.PersonalInfoDTO;
import org.springframework.stereotype.Service;


@Service
public interface PersonalInforServer {

    PersonalInfoDTO createPersonalInfor(PersonalInfoDTO personalInfoDTO);

    PersonalInfoDTO updatePersonalInfor(PersonalInfoDTO personalInfoDTO, Integer personalInforId);
}