package com.noloafing.domain.dto;

import lombok.Data;

@Data
public class RegistUserDto {
    private String userName;
    private String nickName;
    private String email;
    private String password;
    private String rePassword;
    private String emailCode;
}
