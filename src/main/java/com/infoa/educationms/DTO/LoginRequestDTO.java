package com.infoa.educationms.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String accountNumber;
    private String password;
}
