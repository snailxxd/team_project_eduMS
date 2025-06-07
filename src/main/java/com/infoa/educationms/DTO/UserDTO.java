package com.infoa.educationms.DTO;

import lombok.Data;
@Data
public class UserDTO {
    private Integer userId;
    private String accountNumber;
    private Integer personalInforId;
    private String type;
    private String name;
    private String phoneNumber;
    private String picture;
}