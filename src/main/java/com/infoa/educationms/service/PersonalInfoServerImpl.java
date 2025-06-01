package com.infoa.educationms.service;

import com.infoa.educationms.DTO.PersonalInfoDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


;

@Service
public class PersonalInfoServerImpl implements PersonalInforServer {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;


    @Override
    public PersonalInfoDTO createPersonalInfor(PersonalInfoDTO personalInfoDTO){

        PersonalInfor personalInfor = new PersonalInfor();
        personalInfor.setPersonalInfoId(personalInfoDTO.getPersonalInforId());
        personalInfor.setName(personalInfoDTO.getName());
        personalInfor.setPicture(personalInfoDTO.getPicture());
        personalInfor.setPhoneNumber(personalInfoDTO.getPhoneNumber());
        personalInfoRepository.save(personalInfor);
        return personalInfoDTO;
    }

    @Override
    public PersonalInfoDTO updatePersonalInfor(PersonalInfoDTO personalInfoDTO, Integer personalInforId){

        PersonalInfor personalInfor = new PersonalInfor();
        personalInfor.setPersonalInfoId(personalInforId);
        personalInfor.setName(personalInfoDTO.getName());
        personalInfor.setPicture(personalInfoDTO.getPicture());
        personalInfor.setPhoneNumber(personalInfoDTO.getPhoneNumber());
        personalInfoRepository.save(personalInfor);
        return personalInfoDTO;
    }
}