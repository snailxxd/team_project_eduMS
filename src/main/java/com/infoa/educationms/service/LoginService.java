package com.infoa.educationms.service;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.*;

import java.util.List;

public interface LoginService {

    public List<LoginResponseDTO> checkLogin(LoginRequestDTO loginRequestDTO);

}