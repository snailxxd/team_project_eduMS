package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private UserDTO user;
    private String token;
}