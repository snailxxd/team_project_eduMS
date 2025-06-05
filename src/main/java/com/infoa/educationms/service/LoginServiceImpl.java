package com.infoa.educationms.service;

import com.infoa.educationms.DTO.LoginRequestDTO;
import com.infoa.educationms.DTO.LoginResponseDTO;
import com.infoa.educationms.DTO.UserDTO;
import com.infoa.educationms.entities.*;
import com.infoa.educationms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class LoginServiceImpl implements LoginService {



    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;


    @Override
    public List<LoginResponseDTO> checkLogin(LoginRequestDTO loginRequestDTO){
        String account = loginRequestDTO.getAccountNumber();
        String password = loginRequestDTO.getPassword();
        Optional<User> users = userRepository.findByAccountNumber(account);
        if(users.isPresent()){
            User user = users.get();
            if(user.getPassword().equals(password)){
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                PersonalInfor userPersonalInfor = personalInfoRepository.findOneByPersonalInforId(user.getPersonalInfoId());
                UserDTO userDTO = new UserDTO();
                userDTO.setAccountNumber(account);
                userDTO.setName(userPersonalInfor.getName());
                userDTO.setUserId(user.getUserId());
                userDTO.setType(user.getUserType().name());
                userDTO.setPicture(userPersonalInfor.getPicture());
                userDTO.setPhoneNumber(userPersonalInfor.getPhoneNumber());
                loginResponseDTO.setToken(jwtTokenProvider.generateToken(userDTO));
                List<LoginResponseDTO> loginResponseDTOList = new ArrayList<>();
                loginResponseDTOList.add(loginResponseDTO);
                return loginResponseDTOList;
            }
        }

        return new ArrayList<>();
    }

}